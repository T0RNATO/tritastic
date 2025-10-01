package tritastic.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.entity.state.TridentEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;
import tritastic.Tritastic;

import java.util.List;

public class CustomTridentEntityRenderer extends EntityRenderer<TridentEntity, TridentEntityRenderState> {
    private final Identifier TEXTURE;
    private final TridentEntityModel model;

    public CustomTridentEntityRenderer(EntityRendererFactory.Context context, String texture_path) {
        super(context);
        TEXTURE = Tritastic.id(texture_path);
        this.model = new TridentEntityModel(context.getPart(EntityModelLayers.TRIDENT));
    }

    @Override
    public void render(TridentEntityRenderState renderState, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(renderState.pitch + 90.0F));
        List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, renderState.enchanted);

        for (int i = 0; i < list.size(); i++) {
            queue.getBatchingQueue(i)
                    .submitModel(
                            this.model,
                            Unit.INSTANCE,
                            matrixStack,
                            list.get(i),
                            renderState.light,
                            OverlayTexture.DEFAULT_UV,
                            -1,
                            null,
                            renderState.outlineColor,
                            null
                    );
        }        matrixStack.pop();
        super.render(renderState, matrixStack, queue, cameraState);
    }

    public TridentEntityRenderState createRenderState() {
        return new TridentEntityRenderState();
    }

    public void updateRenderState(TridentEntity tridentEntity, TridentEntityRenderState tridentEntityRenderState, float f) {
        super.updateRenderState(tridentEntity, tridentEntityRenderState, f);
        tridentEntityRenderState.yaw = tridentEntity.getLerpedYaw(f);
        tridentEntityRenderState.pitch = tridentEntity.getLerpedPitch(f);
        tridentEntityRenderState.enchanted = tridentEntity.isEnchanted();
    }
}