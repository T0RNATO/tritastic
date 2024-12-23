package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import tritastic.ModEntities;
import tritastic.mixin.TridentEntityAccessor;

public class HellforkEntity extends CustomTridentEntity<HellforkEntity> {
    public HellforkEntity(EntityType<? extends HellforkEntity> type, World world) {
        super(type, world);
    }

    public HellforkEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.HELLFORK, world, owner, stack);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().setOnFireFor(6);
    }

    @Override
    public void tick() {
        super.tick();
        if (!((TridentEntityAccessor) this).getDealtDamage()) {
            var pos = this.getPos();
            this.getWorld().addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }
}
