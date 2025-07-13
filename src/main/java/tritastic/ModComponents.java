package tritastic;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
    protected static void initialise() { }

    public static final ComponentType<Integer> SCULK_CHARGE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Tritastic.id("sculk_charge"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );


}
