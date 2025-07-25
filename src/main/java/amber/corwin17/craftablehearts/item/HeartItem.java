package amber.corwin17.craftablehearts.item;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HeartItem extends Item {
    public HeartItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand){
        if(!world.isClient) {
            player.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(player.getMaxHealth() + 2);
        }
        player.getStackInHand(hand).decrementUnlessCreative(1, player);
        world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.NEUTRAL, 10f, 2f);
        return ActionResult.CONSUME;
    }
}
