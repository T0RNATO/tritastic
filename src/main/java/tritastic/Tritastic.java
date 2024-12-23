package tritastic;

import net.fabricmc.api.ModInitializer;

public class Tritastic implements ModInitializer {
    public static String ID = "tritastic";

    @Override
    public void onInitialize() {
        ModItems.initialise();
        ModEntities.initialise();
    }
}
