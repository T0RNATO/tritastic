package tritastic.datagen;

import tritastic.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LangProvider extends FabricLanguageProvider {
    protected LangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.HELLFORK, "Hellfork");
        translationBuilder.add(ModItems.SOULFORK, "Soulfork");
        translationBuilder.add(ModItems.NIGHTFORK, "Fork of Night");
        translationBuilder.add(ModItems.ECHOFANG, "Echofang");
        translationBuilder.add(ModItems.ENDERFORK, "Enderfork");

        translationBuilder.add(ModItems.GUARDIAN_SPIKE, "Guardian Spike");
//        try {
//            var existingFilePath = dataOutput.getModContainer().findPath("assets/mymod/lang/en_us.existing.json");
//            if (existingFilePath.isPresent()) {
//                translationBuilder.add(existingFilePath.get());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to add existing language file!", e);
//        }
    }
}
