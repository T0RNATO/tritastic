package tritastic;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    protected static void initialize() { }

    public static final ComponentType<Integer> SCULK_CHARGE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Identifier.of(Tritastic.ID, "sculk_charge"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}
