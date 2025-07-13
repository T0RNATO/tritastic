package tritastic;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static void initialise() {
        Registry.register(Registries.PARTICLE_TYPE, Tritastic.id("sculk_explosion"), SCULK_EXPLOSION);
    }

    public static final SimpleParticleType SCULK_EXPLOSION = FabricParticleTypes.simple();
}
