package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import tritastic.ModEntities;
import tritastic.items.Hellfork;
import tritastic.other.TridentEntityDuck;

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
        entityHitResult.getEntity().setOnFireFor(Hellfork.FIRE_DURATION);
    }

    @Override
    public void tick() {
        super.tick();
        if (!((TridentEntityDuck) this).tritastic$getDealtDamage()) {
            var pos = this.getEntityPos();
            this.getEntityWorld().addParticleClient(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }
}
