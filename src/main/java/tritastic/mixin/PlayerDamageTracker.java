package tritastic.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tritastic.items.DamageTracking;

@Mixin(PlayerEntity.class)
public class PlayerDamageTracker {
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;postDamageEntity(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)V"))
    public void damageTracker(Entity target, CallbackInfo ci, @Local(ordinal = 3) float damage, @Local ItemStack item) {
        if (item.getItem() instanceof DamageTracking) {
            ((DamageTracking)item.getItem()).afterHit(item, (LivingEntity) target, damage);
        }
    }
}
