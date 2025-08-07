package tritastic.items;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.entities.SoulforkEntity;
import tritastic.Tritastic;

import java.util.function.Consumer;

public class Soulfork extends CustomTrident<SoulforkEntity> {
    public Soulfork(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/soulfork.png"));
    }
    public static final int WITHER_DURATION = 100;

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return true;
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<SoulforkEntity> newProjectile() {
        return SoulforkEntity::new;
    }

    @Override
    public void use(ItemStack stack, World world, PlayerEntity user, float riptide_strength) {
        if (riptide_strength > 0 && !world.isClient()) {
            user.damage((ServerWorld) world, world.getDamageSources().magic(), 5);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        CustomTrident.tooltip("soulfork").forEach(tooltip);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, WITHER_DURATION / 2, 1), attacker);
    }
}
