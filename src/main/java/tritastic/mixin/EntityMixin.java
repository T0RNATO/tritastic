package tritastic.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tritastic.ModAttachments;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyExpressionValue(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"))
    private boolean cancelEnderforkFallDamage(boolean original) {
        // IJ doesn't like it if I combine the conditions, idk why
        if ((Object)this instanceof PlayerEntity player) {
            if (player.hasAttached(ModAttachments.ENDERFORK_RIPTIDE)) {
                return true;
            }
        }
        return original;
    }
}
