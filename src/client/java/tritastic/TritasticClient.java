package tritastic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.item.Items;
import tritastic.items.CustomTrident;
import tritastic.other.ExpandingTooltip;

public class TritasticClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderers.initialise();
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.getItem().equals(Items.TRIDENT)) {
                list.addAll(1, CustomTrident.tooltip("trident"));
            }
        });

        ParticleFactoryRegistry.getInstance().register(ModParticles.SCULK_EXPLOSION, ExplosionLargeParticle.Factory::new);
        ExpandingTooltip.INSTANCE = Screen::hasShiftDown;
    }
}
