package tritastic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tritastic.ModAttachments;
import tritastic.entities.CustomTridentEntity;
import tritastic.other.TridentEntityDuck;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements TridentEntityDuck {
    @Shadow private boolean dealtDamage;
    @Shadow @Final private static TrackedData<Byte> LOYALTY;

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

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
    private boolean returnTridentToThrownSlot(TridentEntity trident, PlayerEntity player, Operation<Boolean> original) {
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

    @Override
    protected void tickInVoid() {
        if (this.dataTracker.get(LOYALTY) > 0) {
            this.dealtDamage = true;
        } else {
            super.tickInVoid();
        }
    }

    @Override
    public boolean tritastic$getDealtDamage() {
        return this.dealtDamage;
    }
}