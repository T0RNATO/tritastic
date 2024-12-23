package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import tritastic.ModEntities;

public class NightforkEntity extends CustomTridentEntity<NightforkEntity> {
    public NightforkEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.NIGHTFORK, world, owner, stack);
    }

    public NightforkEntity(EntityType<? extends NightforkEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        ((LivingEntity) entityHitResult.getEntity()).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 35, 1), this.getOwner());
    }
}
