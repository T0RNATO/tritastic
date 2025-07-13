package tritastic.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tritastic.ModComponents;

import java.util.List;

@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {
    @Shadow protected int riptideTicks;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @Inject(method = "tickRiptide", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;riptideStack:Lnet/minecraft/item/ItemStack;"))
//    private void awd(Box a, Box b, CallbackInfo ci) {
//        this.removeAttached(ModComponents.ECHOFANG_RIPTIDE);
//        this.removeAttached(ModComponents.ENDERFORK_RIPTIDE);
//    }

    @Inject(method = "tickRiptide", at = @At("TAIL"))
    private void awd(Box a, Box b, CallbackInfo ci) {
        if (this.riptideTicks <= 0) {
            this.removeAttached(ModComponents.ECHOFANG_RIPTIDE);
            if (this.hasAttached(ModComponents.ENDERFORK_RIPTIDE)) {
                this.removeAttached(ModComponents.ENDERFORK_RIPTIDE);
                this.setNoGravity(false);
            }
        }
    }

    @WrapOperation(method = "tickRiptide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<Entity> foo(World instance, Entity entity, Box box, Operation<List<Entity>> original) {
        var enderfork = this.hasAttached(ModComponents.ENDERFORK_RIPTIDE);
        if (this.hasAttached(ModComponents.ECHOFANG_RIPTIDE) || enderfork) {
            if (enderfork) {
                this.move(MovementType.PLAYER, this.getAttached(ModComponents.ENDERFORK_RIPTIDE));
            }
            return List.of();
        }
        return original.call(instance, entity, box);
    }
}
