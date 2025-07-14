package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import tritastic.ModEntities;

public class EnderforkEntity extends CustomTridentEntity<EnderforkEntity> {
    public EnderforkEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.ENDERFORK, world, owner, stack);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        var world = entityHitResult.getEntity().getWorld();
        if (world instanceof ServerWorld server) {
            server.spawnEntity(new EndermiteEntity(EntityType.ENDERMITE, server));
        }
    }

    public EnderforkEntity(EntityType<? extends EnderforkEntity> entityType, World world) {
        super(entityType, world);
    }
}
