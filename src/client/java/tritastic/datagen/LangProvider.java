package tritastic.datagen;

import tritastic.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LangProvider extends FabricLanguageProvider {
    protected LangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
        this.dataOutput = dataOutput;
    }

    private final FabricDataOutput dataOutput;

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.HELLFORK, "Hellfork");
        translationBuilder.add(ModItems.SOULFORK, "Soulfork");
        translationBuilder.add(ModItems.NIGHTFORK, "Fork of Night");
        translationBuilder.add(ModItems.ECHOFANG, "Echofang");
        translationBuilder.add(ModItems.ENDERFORK, "Enderfork");

        translationBuilder.add(ModItems.GUARDIAN_SPIKE, "Guardian Spike");

        try {
            var existingFilePath = dataOutput.getModContainer().findPath("assets/tritastic/lang/en_us.jsonc").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }
}
