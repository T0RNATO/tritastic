package tritastic;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import tritastic.items.*;

import java.util.function.Function;

public class ModItems {
    public static Item register(Function<Item.Settings, Item> factory, Item.Settings settings, String id) {
        Identifier itemID = Tritastic.id(id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);

        return Registry.register(Registries.ITEM, itemID, factory.apply(settings.registryKey(key)));
    }

    public static Item.Settings settings() {
        return new Item.Settings()
                .rarity(Rarity.EPIC)
                .maxDamage(250)
                .enchantable(1);
    }

    public static final Item HELLFORK = register(Hellfork::new, settings()
                    .attributeModifiers(Hellfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Hellfork.createToolComponent()),
            "hellfork"
    );

    public static final Item SOULFORK = register(Soulfork::new, settings()
                    .attributeModifiers(Soulfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Soulfork.createToolComponent()),
            "soulfork"
    );

    public static final Item NIGHTFORK = register(Nightfork::new, settings()
                    .attributeModifiers(Nightfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Nightfork.createToolComponent()),
            "nightfork"
    );

    public static final Item ECHOFANG = register(Echofang::new, settings()
                    .attributeModifiers(Echofang.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Echofang.createToolComponent())
                    .component(ModComponents.SCULK_CHARGE, 0),
            "echofang"
    );

    public static final Item ENDERFORK = register(Enderfork::new, settings()
                    .attributeModifiers(Enderfork.createAttributeModifiers())
                    .component(DataComponentTypes.TOOL, Enderfork.createToolComponent())
                    .useCooldown(1.5f),
            "enderfork"
    );

    public static final Item GUARDIAN_SPIKE = register(Item::new, new Item.Settings(), "guardian_spike");

    public static void initialise() {

    }
}
