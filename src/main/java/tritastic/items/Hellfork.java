package tritastic.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import tritastic.entities.HellforkEntity;
import tritastic.Tritastic;

import java.util.List;

public class Hellfork extends CustomTrident<HellforkEntity> {
    public Hellfork(Item.Settings settings) {
        super(settings, Tritastic.id("textures/entity/hellfork.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.isOnFire() || player.getWorld().getRegistryKey().equals(World.NETHER);
    }

    @Override
    public @NotNull TridentSupplier<HellforkEntity> newProjectile() {
        return HellforkEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(CustomTrident.tooltip("hellfork"));
    }
}
