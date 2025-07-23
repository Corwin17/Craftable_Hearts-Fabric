package amber.corwin17.craftablehearts.screen;

import amber.corwin17.craftablehearts.block.HeartForgeBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class HeartForgeScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final HeartForgeBlockEntity blockEntity;
    public HeartForgeScreenHandler(int syncId,  PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }
    
    public HeartForgeScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.HEART_FORGE_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory)blockEntity;
        this.blockEntity = (HeartForgeBlockEntity) blockEntity;
        this.propertyDelegate = arrayPropertyDelegate;

        this.addSlot(new Slot(inventory, 0, 42, 24){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.DIAMOND);
            }
        });
        this.addSlot(new Slot(inventory, 1, 42, 45){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.GLISTERING_MELON_SLICE);
            }
        });
        this.addSlot(new Slot(inventory, 2, 63, 24){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.NETHER_STAR);
            }
        });
        this.addSlot(new Slot(inventory, 3, 63, 45){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.ENCHANTED_GOLDEN_APPLE);
            }
        });
        this.addSlot(new Slot(inventory, 4, 113, 34){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack insertStack(ItemStack stack, int count) {
                return super.insertStack(stack, count);
            }
        });
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size() - 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size() - 1, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
