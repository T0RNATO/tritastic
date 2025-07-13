package tritastic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tritastic.ModAttachments;
import tritastic.ModComponents;
import tritastic.entities.CustomTridentEntity;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin {
    @ModifyArg(
        method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V"
        ),
        index = 0
    )
    private static EntityType<?> useCustomEntityType(EntityType<?> original) {
        return CustomTridentEntity.CREATING.get();
    }

    @WrapOperation(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;tryPickup(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private boolean awd(TridentEntity trident, PlayerEntity player, Operation<Boolean> original) {
        var returnSlot = trident.getAttachedOrElse(ModAttachments.TRIDENT_SLOT_ATTACHMENT, null);

        if (returnSlot != null) {
            if (returnSlot == -1) {
                if (player.getStackInHand(Hand.OFF_HAND).isEmpty()) {
                    player.setStackInHand(Hand.OFF_HAND, trident.getItemStack());
                    return true;
                }
            } else if (player.getInventory().getStack(returnSlot).isEmpty()) {
                player.getInventory().setStack(returnSlot, trident.getItemStack());
                return true;
            }
        }

        return original.call(trident, player);
    }
}