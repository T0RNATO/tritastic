package tritastic.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
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
import net.minecraft.util.*;
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

    @FunctionalInterface
    public interface TridentSupplier<T> {
        T apply(ServerWorld world, PlayerEntity player, ItemStack stack);
    }

    public final Identifier ENTITY_TEXTURE;

    public abstract boolean riptideCondition(PlayerEntity player, ItemStack item);
    public abstract @NotNull TridentSupplier<T> newProjectile();

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

    static boolean isAboutToBreak(ItemStack stack) {
        return stack.getDamage() >= stack.getMaxDamage() - 1;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;

            if (i < 10) return;

            // 0.75 * (riptide_level + 1), or 0 if no riptide
            float riptide_strength = riptideStrengthMultiplier(EnchantmentHelper.getTridentSpinAttackStrength(stack, player));

            if (riptide_strength > 0.0F && !riptideCondition(player, stack)) return;
            if (isAboutToBreak(stack)) return;

            use(stack, world, player, riptide_strength);

            RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
            player.incrementStat(Stats.USED.getOrCreateStat(this));

            if (world instanceof ServerWorld serverWorld) {
                stack.damage(1, player, LivingEntity.getSlotForHand(user.getActiveHand()));
                if (riptide_strength == 0.0F) {
                    T tridentEntity = newProjectile().apply(serverWorld, player, stack);
                    tridentEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2.5F, 1.0F);
                    var hand = user.getActiveHand();
                    switch (hand) {
                        case MAIN_HAND -> tridentEntity.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, player.getInventory().selectedSlot);
                        case OFF_HAND -> tridentEntity.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, -1);
                    }
                    if (player.isInCreativeMode()) {
                        tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    } else {
                        player.getInventory().removeOne(stack);
                    }

                    world.playSoundFromEntity(null, tridentEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    return;
                }
            }

            if (riptide_strength > 0.0F) {
                this.onRiptide(stack, world, player, riptide_strength);
            }
        }
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (isAboutToBreak(stack)) {
            return TypedActionResult.fail(stack);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(stack, user) > 0.0F && !riptideCondition(user, stack)) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
    }
}
