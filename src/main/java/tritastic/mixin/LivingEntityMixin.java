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
import tritastic.ModAttachments;
import tritastic.ModComponents;

import java.util.List;

@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {
    @Shadow protected int riptideTicks;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tickRiptide", at = @At("TAIL"))
    private void awd(Box a, Box b, CallbackInfo ci) {
        if (this.riptideTicks <= 0) {
            this.removeAttached(ModAttachments.ECHOFANG_RIPTIDE);
            if (this.hasAttached(ModAttachments.ENDERFORK_RIPTIDE)) {
                this.removeAttached(ModAttachments.ENDERFORK_RIPTIDE);
                this.setNoGravity(false);
            }
        }
    }

    @WrapOperation(method = "tickRiptide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<Entity> foo(World world, Entity entity, Box box, Operation<List<Entity>> original) {
        var enderfork = this.hasAttached(ModAttachments.ENDERFORK_RIPTIDE);
        if (this.hasAttached(ModAttachments.ECHOFANG_RIPTIDE) || enderfork) {
            if (enderfork) {
                var attachment = this.getAttached(ModAttachments.ENDERFORK_RIPTIDE);
                this.move(MovementType.PLAYER, attachment.direction());
                if (!attachment.hasPassedThroughBlocks()) {
                    if (!world.getBlockState(this.getBlockPos()).isAir()) {
                        this.setAttached(ModAttachments.ENDERFORK_RIPTIDE, attachment.enterBlocks());
                    }
                } else if (world.getBlockState(this.getBlockPos()).isAir() && world.getBlockState(this.getBlockPos().up()).isAir()) {
                    this.riptideTicks = 0;
                }
            }
            return List.of();
        }
        return original.call(world, entity, box);
    }
}
