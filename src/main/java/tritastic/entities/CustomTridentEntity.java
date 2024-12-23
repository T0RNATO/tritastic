package tritastic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class CustomTridentEntity<T extends CustomTridentEntity<T>> extends TridentEntity {
    public static ThreadLocal<EntityType<?>> CREATING = ThreadLocal.withInitial(() -> EntityType.TRIDENT);

    public CustomTridentEntity(EntityType<? extends T> entityType, World world, LivingEntity owner, ItemStack stack) {
        super(setEntityType(world, entityType), owner, stack);
        CREATING.set(EntityType.TRIDENT);
    }

    private static <H> H setEntityType(H value, EntityType<?> type) {
        CREATING.set(type);
        return value;
    }

    public CustomTridentEntity(EntityType<? extends T> entityType, World world) {
        super(entityType, world);
    }
}
