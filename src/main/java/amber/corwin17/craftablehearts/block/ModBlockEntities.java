package amber.corwin17.craftablehearts.block;

import amber.corwin17.craftablehearts.CraftableHearts;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<HeartForgeBlockEntity> HEART_FORGE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CraftableHearts.MOD_ID, "heart_forge_block_entity"),
            FabricBlockEntityTypeBuilder.create(HeartForgeBlockEntity::new, ModBlocks.HEART_FORGE).build(null));
    
    public static void registerBlockEntities() {
        CraftableHearts.LOGGER.info("Registering Block Entities for " + CraftableHearts.MOD_ID);
    }
}
