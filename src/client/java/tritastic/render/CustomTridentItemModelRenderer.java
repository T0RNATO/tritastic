package tritastic.render;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import tritastic.items.CustomTrident;

import java.util.Set;

public class CustomTridentItemModelRenderer implements SpecialModelRenderer<Identifier> {
    private final TridentEntityModel model;

    public CustomTridentItemModelRenderer(TridentEntityModel model) {
        this.model = model;
    }

    @Override
    public void render(@Nullable Identifier data, ItemDisplayContext displayContext, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, boolean glint, int i) {
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        queue.submitModelPart(this.model.getRootPart(), matrices, this.model.getLayer(data), light, overlay, null, false, glint, -1, null, i);
        matrices.pop();
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.getRootPart().collectVertices(matrixStack, vertices);
    }

    @Override
    public @Nullable Identifier getData(ItemStack stack) {
        return ((CustomTrident<?>) stack.getItem()).ENTITY_TEXTURE;
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<CustomTridentItemModelRenderer.Unbaked> CODEC = MapCodec.unit(new CustomTridentItemModelRenderer.Unbaked());

        @Override
        public MapCodec<CustomTridentItemModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(BakeContext context) {
            return new CustomTridentItemModelRenderer(new TridentEntityModel(context.entityModelSet().getModelPart(EntityModelLayers.TRIDENT)));
        }
    }
}
