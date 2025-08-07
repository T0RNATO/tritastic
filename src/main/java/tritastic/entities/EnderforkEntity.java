package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
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
        var entity = entityHitResult.getEntity();
        if (entity.getWorld() instanceof ServerWorld server) {
            EntityType.ENDERMITE.spawn(server, entity.getBlockPos(), SpawnReason.MOB_SUMMONED);
        }
    }

    public EnderforkEntity(EntityType<? extends EnderforkEntity> entityType, World world) {
        super(entityType, world);
    }
}
