package tritastic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import tritastic.entities.EchofangEntity;
import tritastic.entities.HellforkEntity;
import tritastic.entities.NightforkEntity;
import tritastic.entities.SoulforkEntity;

public class ModEntities {
    private static <T extends Entity> EntityType<T> register(String id,  EntityType. EntityFactory<T> factory) {
        var key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Tritastic.ID, id));

        var builder = EntityType.Builder.create(factory, SpawnGroup.MISC)
                .dropsNothing()
                .dimensions(0.5F, 0.5F)
                .eyeHeight(0.13F)
                .maxTrackingRange(4)
                .trackingTickInterval(20);

        return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
    }

    public static final EntityType<HellforkEntity> HELLFORK = register("hellfork", HellforkEntity::new);
    public static final EntityType<SoulforkEntity> SOULFORK = register("soulfork", SoulforkEntity::new);
    public static final EntityType<NightforkEntity> NIGHTFORK = register("nightfork", NightforkEntity::new);
    public static final EntityType<EchofangEntity> ECHOFANG = register("echofang", EchofangEntity::new);

    public static void initialise() {}
}
