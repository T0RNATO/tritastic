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
import tritastic.entities.HellforkEntity;
import tritastic.Tritastic;

import java.util.function.Consumer;

public class Hellfork extends CustomTrident<HellforkEntity> {
    public Hellfork(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/hellfork.png"));
    }
    public static final int FIRE_DURATION = 6;

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.isOnFire() || player.getEntityWorld().getRegistryKey().equals(World.NETHER);
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<HellforkEntity> newProjectile() {
        return HellforkEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        CustomTrident.tooltip("hellfork").forEach(tooltip);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        target.setOnFireFor(FIRE_DURATION);
    }
}
