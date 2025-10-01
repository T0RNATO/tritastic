package tritastic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import tritastic.entities.*;

public class ModEntities {
    private static <T extends Entity> EntityType<T> register(String id,  EntityType. EntityFactory<T> factory) {
        var key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Tritastic.id(id));

        var builder = EntityType.Builder.create(factory, SpawnGroup.MISC)
                .dimensions(0.5F, 0.5F)
                .eyeHeight(0.13F)
                .maxTrackingRange(4)
                .trackingTickInterval(20);

        return Registry.register(Registries.ENTITY_TYPE, key, builder.build(id));
    }

    public static final EntityType<HellforkEntity> HELLFORK = register("hellfork", HellforkEntity::new);
    public static final EntityType<SoulforkEntity> SOULFORK = register("soulfork", SoulforkEntity::new);
    public static final EntityType<NightforkEntity> NIGHTFORK = register("nightfork", NightforkEntity::new);
    public static final EntityType<EchofangEntity> ECHOFANG = register("echofang", EchofangEntity::new);
    public static final EntityType<EnderforkEntity> ENDERFORK = register("enderfork", EnderforkEntity::new);

    public static void initialise() {}
}
