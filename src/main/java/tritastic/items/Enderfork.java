package tritastic.items;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.ModComponents;
import tritastic.Tritastic;
import tritastic.entities.EnderforkEntity;

import java.util.function.Consumer;

public class Enderfork extends CustomTrident<EnderforkEntity> {
    public Enderfork(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/enderfork.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return true;
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<EnderforkEntity> newProjectile() {
        return EnderforkEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        CustomTrident.tooltip(null, "Spawn an endermite", "None. Ignores blocks and gravity.")
                .forEach(tooltip);
    }

    @Override
    public void onRiptide(ItemStack stack, World world, PlayerEntity player, float strength) {
        var ticks = MathHelper.ceil(strength / 0.75) * 5;
        player.setAttached(ModComponents.ENDERFORK_RIPTIDE, player.getRotationVector());
        player.useRiptide(ticks, 8.0F, stack);
        player.setNoGravity(true);
        RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
        world.playSoundFromEntity(null, player, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
