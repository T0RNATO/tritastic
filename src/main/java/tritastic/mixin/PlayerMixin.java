package tritastic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tritastic.ModAttachments;
import tritastic.entities.EchofangEntity;
import tritastic.items.DamageTracking;

@Debug(export = true)
@Mixin(PlayerEntity.class)
abstract public class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;postHit(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    public void damageTracker(Entity target, CallbackInfo ci, @Local(ordinal = 3) float damage, @Local ItemStack item) {
        if (item.getItem() instanceof DamageTracking dtItem) {
            dtItem.afterHit(item, (LivingEntity) target, damage);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void echofangExplosion(CallbackInfo ci) {
        if (this.isUsingRiptide() && (this.horizontalCollision || this.verticalCollision) && this.getAttachedOrElse(ModAttachments.ECHOFANG_RIPTIDE, false)) {
            this.setAttached(ModAttachments.ECHOFANG_RIPTIDE, false);
            EchofangEntity.explode(2, this.getPos(), this.getWorld(), this);
            this.riptideTicks = 1;
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    private boolean noClipWithEnderfork(PlayerEntity instance, Operation<Boolean> original) {
        return this.hasAttached(ModAttachments.ENDERFORK_RIPTIDE) || original.call(instance);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelEchofangFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (Boolean.FALSE.equals(this.getAttached(ModAttachments.ECHOFANG_RIPTIDE))) {
            cir.cancel();
        }
    }
}
