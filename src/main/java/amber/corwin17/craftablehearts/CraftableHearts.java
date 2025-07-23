package amber.corwin17.craftablehearts;

import amber.corwin17.craftablehearts.block.ModBlockEntities;
import amber.corwin17.craftablehearts.block.ModBlocks;
import amber.corwin17.craftablehearts.item.ModItems;
import amber.corwin17.craftablehearts.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftableHearts implements ModInitializer {
	public static final String MOD_ID = "craftablehearts";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}