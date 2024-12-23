package tritastic.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.entities.HellforkEntity;
import tritastic.Tritastic;

import java.util.List;

public class Hellfork extends CustomTrident<HellforkEntity> {
    public Hellfork(Item.Settings settings) {
        super(settings, Identifier.of(Tritastic.ID, "textures/entity/hellfork.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.isOnFire() || player.getWorld().getRegistryKey().equals(World.NETHER);
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<HellforkEntity> newProjectile() {
        return HellforkEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(CustomTrident.tooltip(null, "Applies fire", "While on fire or in the nether"));
    }
}
