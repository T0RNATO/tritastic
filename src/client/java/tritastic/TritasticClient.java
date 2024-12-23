package tritastic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Items;
import tritastic.items.CustomTrident;

public class TritasticClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderers.initialise();
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.getItem().equals(Items.TRIDENT)) {
                list.addAll(1, CustomTrident.tooltip(null, null, "Whilst in water or rain"));
            }
        });
    }
}
