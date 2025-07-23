package amber.corwin17.craftablehearts.screen;

import amber.corwin17.craftablehearts.CraftableHearts;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HeartForgeScreen extends HandledScreen<HeartForgeScreenHandler> {
    public static final Identifier GUI_TEXTURE = Identifier.of(CraftableHearts.MOD_ID, "textures/gui/heart_forge/heart_forge_gui.png");
    private static final Identifier ARROW_TEXTURE = Identifier.of(CraftableHearts.MOD_ID, "textures/gui/arrow_progress.png");
    
    public HeartForgeScreen(HeartForgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 83, y + 35, 0, 0,
                    handler.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context,  mouseX, mouseY);
    }
}
