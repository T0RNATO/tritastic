package tritastic.render;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import tritastic.items.CustomTrident;

public class CustomTridentItemModelRenderer implements SpecialModelRenderer<Identifier> {
    private final TridentEntityModel model;

    public CustomTridentItemModelRenderer(TridentEntityModel model) {
        this.model = model;
    }

    @Override
    public void render(@Nullable Identifier texture_id, ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(texture_id), false, glint);
        this.model.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
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
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new CustomTridentItemModelRenderer(new TridentEntityModel(entityModels.getModelPart(EntityModelLayers.TRIDENT)));
        }
    }
}
