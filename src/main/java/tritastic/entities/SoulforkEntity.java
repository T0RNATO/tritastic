package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import tritastic.ModEntities;
import tritastic.other.TridentEntityDuck;

public class SoulforkEntity extends CustomTridentEntity<SoulforkEntity> {
    public SoulforkEntity(EntityType<? extends SoulforkEntity> entityType, World world) {
        super(entityType, world);
    }

    public SoulforkEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.SOULFORK, world, owner, stack);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        ((LivingEntity) entityHitResult.getEntity()).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1), this.getOwner());
    }

    @Override
    public void tick() {
        super.tick();
        if (!((TridentEntityDuck) this).tritastic$getDealtDamage()) {
            var pos = this.getPos();
            this.getWorld().addParticleClient(ParticleTypes.SOUL, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }
}
