package tritastic.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockParticleEffect;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import tritastic.ModComponents;
import tritastic.ModEntities;
import tritastic.ModParticles;

import java.util.Optional;

public class EchofangEntity extends CustomTridentEntity<EchofangEntity> {
    public EchofangEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.ECHOFANG, world, owner, stack);
    }

    private static final ExplosionBehavior behavior = new AdvancedExplosionBehavior(false, true, Optional.of(1.0F), Optional.empty());
    private static final Pool<BlockParticleEffect> EXPLOSION_BLOCK_PARTICLES = Pool.empty();

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        var explosion_power = this.getItemStack().getOrDefault(ModComponents.SCULK_CHARGE, 0) / 150;
        var pos = this.getEntityPos();
        EchofangEntity.explode(explosion_power, pos, this.getEntityWorld(), this);

        var new_stack = this.getItemStack().copy();
        new_stack.set(ModComponents.SCULK_CHARGE, 0);
        this.setStack(new_stack);
    }

    public static void explode(float power, Vec3d pos, World world, Entity entity) {
        world.createExplosion(
                entity,
                Explosion.createDamageSource(world, entity),
                behavior,
                pos.x, pos.y, pos.z,
                power,
                false,
                World.ExplosionSourceType.MOB,
                ModParticles.SCULK_EXPLOSION,
                ModParticles.SCULK_EXPLOSION,
                EXPLOSION_BLOCK_PARTICLES,
                SoundEvents.ENTITY_GENERIC_EXPLODE
        );
    }

    public EchofangEntity(EntityType<? extends EchofangEntity> entityType, World world) {
        super(entityType, world);
    }
}
