package tritastic.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface DamageTracking {
    void afterHit(ItemStack stack, LivingEntity target, float damage);
}
