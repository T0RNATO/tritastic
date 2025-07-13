package tritastic;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.minecraft.util.math.Vec3d;

public class ModComponents {
    protected static void initialise() { }

    public static final ComponentType<Integer> SCULK_CHARGE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Tritastic.id("sculk_charge"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final AttachmentType<Integer> TRIDENT_SLOT_ATTACHMENT = AttachmentRegistry.create(Tritastic.id("trident_slot"));
    public static final AttachmentType<Boolean> ECHOFANG_RIPTIDE = AttachmentRegistry.create(Tritastic.id("echofang_riptide"));
    public static final AttachmentType<Vec3d> ENDERFORK_RIPTIDE = AttachmentRegistry.create(Tritastic.id("enderfork_riptide"));

//    public static record EnderforkDirection(Vec3d vec) {
//        public static Codec<EnderforkDirection> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//                Codec.FLOAT.listOf().fieldOf("floatList").forGetter(ModCustomAttachedData::floatList) // our object just has a list of floats
//        ).apply(instance, ModCustomAttachedData::new));
//    }
}
