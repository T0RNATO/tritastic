package tritastic;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import tritastic.render.CustomTridentEntityRenderer;

public class ModRenderers {
    public static void initialise() {
        EntityRendererRegistry.register(ModEntities.HELLFORK, ctx -> new CustomTridentEntityRenderer(ctx, "textures/entity/hellfork.png"));
        EntityRendererRegistry.register(ModEntities.SOULFORK, ctx -> new CustomTridentEntityRenderer(ctx, "textures/entity/soulfork.png"));
        EntityRendererRegistry.register(ModEntities.NIGHTFORK, ctx -> new CustomTridentEntityRenderer(ctx, "textures/entity/nightfork.png"));
        EntityRendererRegistry.register(ModEntities.ECHOFANG, ctx -> new CustomTridentEntityRenderer(ctx, "textures/entity/echofang.png"));
    }
}