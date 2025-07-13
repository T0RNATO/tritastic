package tritastic.items;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.ModComponents;
import tritastic.Tritastic;
import tritastic.entities.EchofangEntity;

import java.util.function.Consumer;

public class Echofang extends CustomTrident<EchofangEntity> implements DamageTracking {
    public static int MAX_CHARGE = 500;

    public Echofang(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/echofang.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.getPitch() > 0;
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<EchofangEntity> newProjectile() {
        return EchofangEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        CustomTrident.tooltip("Charges up stored sculk explosion", "Detonates stored sculk explosion", "Must face towards the ground. Detonates a free sculk explosion on impact and cancels fall damage")
                .forEach(tooltip);
    }

    @Override
    public void afterHit(ItemStack stack, LivingEntity target, float damage) {
        var charge = stack.getOrDefault(ModComponents.SCULK_CHARGE, 0);
        stack.set(ModComponents.SCULK_CHARGE, Math.min(charge + Math.round(damage * 10), MAX_CHARGE));
    }

    @Override
    public void onRiptide(ItemStack stack, World world, PlayerEntity player, float strength) {
        super.onRiptide(stack, world, player, strength);
        player.setAttached(ModComponents.ECHOFANG_RIPTIDE, true);
    }
}
