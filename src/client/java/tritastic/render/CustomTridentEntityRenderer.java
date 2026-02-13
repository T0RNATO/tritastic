package tritastic.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.entity.state.TridentEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;
import tritastic.Tritastic;

import java.util.List;

public class CustomTridentEntityRenderer extends TridentEntityRenderer {
    private final Identifier TEXTURE;
    private final TridentEntityModel model;

    public CustomTridentEntityRenderer(EntityRendererFactory.Context context, String texture_path) {
        super(context);
        TEXTURE = Tritastic.id(texture_path);
        this.model = new TridentEntityModel(context.getPart(EntityModelLayers.TRIDENT));
    }

    @Override
    public void render(TridentEntityRenderState tridentEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(tridentEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tridentEntityRenderState.pitch + 90.0F));
        List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, tridentEntityRenderState.enchanted);

        for(int i = 0; i < list.size(); ++i) {
            orderedRenderCommandQueue.getBatchingQueue(i).submitModel(this.model, Unit.INSTANCE, matrixStack, list.get(i), tridentEntityRenderState.light, OverlayTexture.DEFAULT_UV, -1, null, tridentEntityRenderState.outlineColor, null);
        }

        matrixStack.pop();
    }
}