package tritastic.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.ModAttachments;
import tritastic.Tritastic;
import tritastic.entities.EnderforkEntity;

import java.util.List;

public class Enderfork extends CustomTrident<EnderforkEntity> {
    public Enderfork(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/enderfork.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return true;
    }

    @Override
    public @NotNull TridentSupplier<EnderforkEntity> newProjectile() {
        return EnderforkEntity::new;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        var hasRiptide = EnchantmentHelper.getTridentSpinAttackStrength(stack, user) > 0.0F;
        if (isAboutToBreak(stack)) {
            return TypedActionResult.fail(stack);
        } else if (hasRiptide && !riptideCondition(user, stack)) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            if (hasRiptide) {
                user.getItemCooldownManager().set(this, 35);
            }
            return TypedActionResult.consume(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(CustomTrident.tooltip("enderfork"));
    }

    @Override
    public void onRiptide(ItemStack stack, World world, PlayerEntity player, float strength) {
        var ticks = MathHelper.ceil(strength / 0.75) * 5;
        player.setAttached(ModAttachments.ENDERFORK_RIPTIDE, ModAttachments.Enderfork.create(player.getRotationVector()));
        player.useRiptide(ticks, 8.0F, stack);
        player.setNoGravity(true);
        RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
        world.playSoundFromEntity(null, player, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (world.isClient) {
            player.sendMessage(Text.literal("Sneak to exit"), true);
        }
    }
}
