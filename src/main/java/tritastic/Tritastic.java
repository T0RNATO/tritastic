package tritastic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import tritastic.other.ExpandingTooltip;

public class Tritastic implements ModInitializer {
    public static String ID = "tritastic";

    public static Identifier id(String str) {
        return Identifier.of(ID, str);
    }

    @Override
    public void onInitialize() {
        ModItems.initialise();
        ModEntities.initialise();
        ModParticles.initialise();
        ModComponents.initialise();
        ModAttachments.initialise();

        var elder_guardian_table = Identifier.ofVanilla("entities/elder_guardian");

        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (registryKey.getValue().equals(elder_guardian_table)) {
                var pool = LootPool.builder().with(ItemEntry.builder(ModItems.GUARDIAN_SPIKE).build()).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,3)));
                builder.pool(pool);
            }
        });
        ExpandingTooltip.INSTANCE = () -> false;
    }
}
