package amber.corwin17.craftablehearts;

import amber.corwin17.craftablehearts.block.HeartForgeBlockEntityRenderer;
import amber.corwin17.craftablehearts.block.ModBlockEntities;
import amber.corwin17.craftablehearts.screen.HeartForgeScreen;
import amber.corwin17.craftablehearts.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CraftableHeartsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.HEART_FORGE_BLOCK_ENTITY, HeartForgeBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.HEART_FORGE_SCREEN_HANDLER, HeartForgeScreen::new);
    }
}
