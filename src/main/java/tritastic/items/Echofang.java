package tritastic.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import tritastic.ModComponents;
import tritastic.Tritastic;
import tritastic.entities.EchofangEntity;

import java.util.List;

public class Echofang extends CustomTrident<EchofangEntity> implements DamageTracking {
    public static int MAX_CHARGE = 500;

    public Echofang(Item.Settings settings) {
        super(settings, Identifier.of(Tritastic.ID, "textures/entity/echofang.png"));
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
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(CustomTrident.tooltip("Charges up sculk explosion", "Detonates stored sculk explosion", "Must face towards the ground. Detonates a free sculk explosion on impact"));
    }

    @Override
    public void afterHit(ItemStack stack, LivingEntity target, float damage) {
        var charge = stack.get(ModComponents.SCULK_CHARGE);
        if (charge != null) {
            stack.set(ModComponents.SCULK_CHARGE, Math.min(charge + Math.round(damage * 10), MAX_CHARGE));
        }
    }
}
