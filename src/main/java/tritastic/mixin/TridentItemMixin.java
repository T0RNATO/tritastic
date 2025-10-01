package tritastic.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tritastic.ModAttachments;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @ModifyExpressionValue(method = "onStoppedUsing", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/projectile/TridentEntity;"))
    private TridentEntity storeThrownSlot(TridentEntity original, @Local(argsOnly = true) LivingEntity user) {
        var hand = user.getActiveHand();
        if (user instanceof PlayerEntity player) {
            switch (hand) {
                case MAIN_HAND -> original.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, player.getInventory().selectedSlot);
                case OFF_HAND -> original.setAttached(ModAttachments.TRIDENT_SLOT_ATTACHMENT, -1);
            }
        }
        return original;
    }
}
