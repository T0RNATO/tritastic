package tritastic.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.entity.state.TridentEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import tritastic.Tritastic;

public class CustomTridentEntityRenderer extends EntityRenderer<TridentEntity, TridentEntityRenderState> {
    private final Identifier TEXTURE;
    private final TridentEntityModel model;

    public CustomTridentEntityRenderer(EntityRendererFactory.Context context, String texture_path) {
        super(context);
        TEXTURE = Tritastic.id(texture_path);
        this.model = new TridentEntityModel(context.getPart(EntityModelLayers.TRIDENT));
    }

    public void render(TridentEntityRenderState tridentEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(tridentEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(tridentEntityRenderState.pitch + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
                vertexConsumerProvider, this.model.getLayer(TEXTURE), false, tridentEntityRenderState.enchanted
        );
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(tridentEntityRenderState, matrixStack, vertexConsumerProvider, i);
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