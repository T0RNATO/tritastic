package tritastic.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tritastic.ModComponents;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @ModifyExpressionValue(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;spawnWithVelocity(Lnet/minecraft/entity/projectile/ProjectileEntity$ProjectileCreator;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;FFF)Lnet/minecraft/entity/projectile/ProjectileEntity;"))
    private ProjectileEntity awd(ProjectileEntity original, @Local(argsOnly = true) LivingEntity user) {
        var hand = user.getActiveHand();
        if (user instanceof PlayerEntity player) {
            switch (hand) {
                case MAIN_HAND -> original.setAttached(ModComponents.TRIDENT_SLOT_ATTACHMENT, player.getInventory().getSelectedSlot());
                case OFF_HAND -> original.setAttached(ModComponents.TRIDENT_SLOT_ATTACHMENT, -1);
            }
        }
        return original;
    }
}
