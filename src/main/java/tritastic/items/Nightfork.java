package tritastic.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import tritastic.entities.NightforkEntity;
import tritastic.Tritastic;

import java.util.List;

public class Nightfork extends CustomTrident<NightforkEntity> {
    public Nightfork(Item.Settings settings) {
        super(settings, Identifier.of(Tritastic.ID, "textures/entity/nightfork.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.getWorld().isNight();
    }

    @Override
    public float riptideStrengthMultiplier(float strength) {
        return (float) (0.55 * strength);
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<NightforkEntity> newProjectile() {
        return NightforkEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(CustomTrident.tooltip(null, "Applies blindness", "Nighttime"));
    }
}
