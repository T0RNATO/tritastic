package tritastic.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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
}