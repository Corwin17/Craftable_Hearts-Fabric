package amber.corwin17.craftablehearts.screen;

import amber.corwin17.craftablehearts.CraftableHearts;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<HeartForgeScreenHandler> HEART_FORGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CraftableHearts.MOD_ID, "heart_forge_screen_handler"),
            new ExtendedScreenHandlerType<>(HeartForgeScreenHandler::new, BlockPos.PACKET_CODEC));
    
    public static void registerScreenHandlers() {
        CraftableHearts.LOGGER.info("Registering Screen Handlers for " + CraftableHearts.MOD_ID);
    }
}
