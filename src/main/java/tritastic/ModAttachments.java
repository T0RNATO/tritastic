package tritastic;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class ModAttachments {
    public static void initialise() {}

    public static final AttachmentType<Integer> TRIDENT_SLOT_ATTACHMENT = AttachmentRegistry.create(Tritastic.id("trident_slot"));
    public static final AttachmentType<Boolean> ECHOFANG_RIPTIDE = AttachmentRegistry.create(Tritastic.id("echofang_riptide"));
    public static final AttachmentType<Enderfork> ENDERFORK_RIPTIDE = AttachmentRegistry.create(Tritastic.id("enderfork_riptide"));

    public record Enderfork(Vec3d direction, boolean hasPassedThroughBlocks) {
        public static Enderfork create(Vec3d direction) {
            return new Enderfork(direction, false);
        }

        public Enderfork enterBlocks() {
            return new Enderfork(direction, true);
        }
    }
}
