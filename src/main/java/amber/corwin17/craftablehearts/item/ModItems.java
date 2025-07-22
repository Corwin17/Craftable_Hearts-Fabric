package amber.corwin17.craftablehearts.item;

import amber.corwin17.craftablehearts.CraftableHearts;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item HEART = registerItem("heart", HeartItem::new);
    
    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(CraftableHearts.MOD_ID, name), function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(CraftableHearts.MOD_ID, name)))));
    }
    
    public static void registerModItems() {
        CraftableHearts.LOGGER.info("Registering Mod Items for "  + CraftableHearts.MOD_ID);
    }
}
