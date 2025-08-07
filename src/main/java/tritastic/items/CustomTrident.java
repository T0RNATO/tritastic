package tritastic.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.ModAttachments;
import tritastic.entities.CustomTridentEntity;
import tritastic.other.ExpandingTooltip;

import java.util.List;

public abstract class CustomTrident<T extends CustomTridentEntity<T>> extends TridentItem {
    public CustomTrident(Item.Settings settings, Identifier entityTexture) {
        super(settings);
        ENTITY_TEXTURE = entityTexture;
    }

    public final Identifier ENTITY_TEXTURE;

    public abstract boolean riptideCondition(PlayerEntity player, ItemStack item);
    public abstract @NotNull ProjectileEntity.ProjectileCreator<T> newProjectile();

    public void onRiptide(ItemStack stack, World world, PlayerEntity player, float riptide_strength) {
        Vec3d motion = getMotion(player, riptide_strength);
        player.addVelocity(motion);

        player.useRiptide(20, 8.0F, stack);

        if (player.isOnGround()) {
            player.move(MovementType.SELF, new Vec3d(0.0, riptideStrengthMultiplier(1.2F), 0.0));
        }

        RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
        world.playSoundFromEntity(null, player, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    public void use(ItemStack stack, World world, PlayerEntity user, float riptide_strength) { }
    public float riptideStrengthMultiplier(float strength) {return strength;}

    private static MutableText tooltipEntry(String key) {
        return Text.translatableWithFallback(key, "None").formatted(Formatting.GRAY);
    }

    public static List<MutableText> tooltip(String translationKey) {
        if (!ExpandingTooltip.INSTANCE.isHoldingShift()) {
            return List.of(Text.literal("[Shift to Expand]").formatted(Formatting.DARK_GRAY));
        }
        return List.of(
            Text.literal("  Riptide Condition: ").append(tooltipEntry("tooltip.tritastic." + translationKey + ".riptide")),
            Text.literal("  Hit Effect: ").append(tooltipEntry("tooltip.tritastic." + translationKey + ".onhit")),
            Text.literal("  Melee Effect: ").append(tooltipEntry("tooltip.tritastic." + translationKey + ".melee"))
        );
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;

            if (i < 10) return false;

            // 0.75 * (riptide_level + 1), or 0 if no riptide
            float riptide_strength = riptideStrengthMultiplier(EnchantmentHelper.getTridentSpinAttackStrength(stack, player));

            if (riptide_strength > 0.0F && !riptideCondition(player, stack)) return false;
            if (stack.willBreakNextUse()) return false;

            use(stack, world, player, riptide_strength);

            RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
            player.incrementStat(Stats.USED.getOrCreateStat(this));

            if (world instanceof ServerWorld serverWorld) {
                stack.damage(1, player);
                if (riptide_strength == 0.0F) {
                    T tridentEntity = ProjectileEntity.spawnWithVelocity(newProjectile(), serverWorld, stack, player, 0.0F, 2.5F, 1.0F);
                    var hand = user.getActiveHand();
                    switch (hand) {
                        case MAIN_HAND -> tridentEntity.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, player.getInventory().getSelectedSlot());
                        case OFF_HAND -> tridentEntity.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, -1);
                    }
                    if (player.isInCreativeMode()) {
                        tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    } else {
                        player.getInventory().removeOne(stack);
                    }

                    world.playSoundFromEntity(null, tridentEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    return true;
                }
            }

            if (riptide_strength > 0.0F) {
                this.onRiptide(stack, world, player, riptide_strength);
                return true;
            }
            return false;
        }
        return false;
    }

    private static Vec3d getMotion(PlayerEntity player, float strength) {
        float yaw = player.getYaw() * (float) (Math.PI / 180.0);
        float pitch = player.getPitch() * (float) (Math.PI / 180.0);

        Vec3d motion = new Vec3d(
                -MathHelper.sin(yaw) * MathHelper.cos(pitch),
                -MathHelper.sin(pitch),
                MathHelper.cos(yaw) * MathHelper.cos(pitch)
        );

        double length = motion.length();

        motion = motion.multiply(strength / length);
        return motion;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.willBreakNextUse()) {
            return ActionResult.FAIL;
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemStack, user) > 0.0F && !riptideCondition(user, itemStack)) {
            return ActionResult.FAIL;
        } else {
            user.setCurrentHand(hand);
            return ActionResult.CONSUME;
        }
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
    }
}
