package tritastic.items;

import net.minecraft.component.type.TooltipDisplayComponent;
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
        CustomTrident.tooltip(null, "Applies wither", "None. Costs 2.5 hearts").forEach(tooltip);
    }
}
