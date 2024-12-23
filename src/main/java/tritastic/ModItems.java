package tritastic;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import tritastic.items.Echofang;
import tritastic.items.Hellfork;
import tritastic.items.Nightfork;
import tritastic.items.Soulfork;

import java.util.function.Function;

public class ModItems {
    public static Item register(Function<Item.Settings, Item> factory, Item.Settings settings, String id) {
        Identifier itemID = Identifier.of(Tritastic.ID, id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);

        return Registry.register(Registries.ITEM, itemID, factory.apply(settings.registryKey(key)));
    }

    public static final Item HELLFORK = register(
            Hellfork::new, new Item.Settings()
                .rarity(Rarity.EPIC)
                .maxDamage(250)
                .attributeModifiers(Hellfork.createAttributeModifiers())
                .component(DataComponentTypes.TOOL, Hellfork.createToolComponent())
                .enchantable(1),
            "hellfork"
    );

    public static final Item SOULFORK = register(
            Soulfork::new, new Item.Settings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(250)
                    .attributeModifiers(Soulfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Soulfork.createToolComponent())
                    .enchantable(1),
            "soulfork"
    );

    public static final Item NIGHTFORK = register(
            Nightfork::new, new Item.Settings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(250)
                    .attributeModifiers(Nightfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Nightfork.createToolComponent())
                    .enchantable(1),
            "nightfork"
    );

    public static final Item ECHOFANG = register(
            Echofang::new, new Item.Settings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(250)
                    .attributeModifiers(Echofang.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Echofang.createToolComponent())
                    .component(ModComponents.SCULK_CHARGE, 0)
                    .enchantable(1),
            "echofang"
    );

    public static void initialise() {

    }
}
