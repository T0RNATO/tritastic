package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tritastic.ModEntities;

public class EnderforkEntity extends CustomTridentEntity<EnderforkEntity> {
    public EnderforkEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.ENDERFORK, world, owner, stack);
    }

    public EnderforkEntity(EntityType<? extends EnderforkEntity> entityType, World world) {
        super(entityType, world);
    }
}
