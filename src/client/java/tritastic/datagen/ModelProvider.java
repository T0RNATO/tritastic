package tritastic.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import tritastic.ModItems;
import tritastic.render.CustomTridentItemModelRenderer;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    public final void registerTrident(ItemModelGenerator gen, Item item, ItemModel.Unbaked gui_model) {
        var in_hand = ItemModels.special(
                ModelIds.getItemSubModelId(Items.TRIDENT, "_in_hand"), new CustomTridentItemModelRenderer.Unbaked()
        );
        var throwing = ItemModels.special(
                ModelIds.getItemSubModelId(Items.TRIDENT, "_throwing"), new CustomTridentItemModelRenderer.Unbaked()
        );
        var condition = ItemModels.condition(ItemModels.usingItemProperty(), throwing, in_hand);
        gen.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(gui_model, condition));
    }

    public final void registerTrident(ItemModelGenerator gen, Item item) {
        registerTrident(gen, item, ItemModels.basic(gen.upload(item, Models.GENERATED)));
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        registerTrident(gen, ModItems.HELLFORK);
        registerTrident(gen, ModItems.SOULFORK);
        registerTrident(gen, ModItems.NIGHTFORK);
        registerTrident(gen, ModItems.ECHOFANG);
        registerTrident(gen, ModItems.ENDERFORK);

        gen.register(ModItems.GUARDIAN_SPIKE, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }
}
