package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tritastic.ModEntities;

public class EchofangEntity extends CustomTridentEntity<EchofangEntity> {
    public EchofangEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.ECHOFANG, world, owner, stack);
    }

    public EchofangEntity(EntityType<? extends EchofangEntity> entityType, World world) {
        super(entityType, world);
    }
}
