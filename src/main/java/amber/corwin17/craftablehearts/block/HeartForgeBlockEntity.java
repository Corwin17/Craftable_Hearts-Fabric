package amber.corwin17.craftablehearts.block;

import amber.corwin17.craftablehearts.item.ModItems;
import amber.corwin17.craftablehearts.screen.HeartForgeScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HeartForgeBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int OUTPUT_SLOT = 4;
    private int progress = 0;
    private int maxProgress = 72;
    private float rotation = 0f;
    protected final PropertyDelegate propertyDelegate;
    
    public HeartForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEART_FORGE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> HeartForgeBlockEntity.this.progress;
                    case 1 -> HeartForgeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: HeartForgeBlockEntity.this.progress = value;
                    case 1: HeartForgeBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    
    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360){
            rotation = 0f;
        }
        return rotation;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, inventory);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        ItemScatterer.spawn(world, pos, (this));
        super.onBlockReplaced(pos, oldState);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Heart Forge");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new HeartForgeScreenHandler(syncId,  playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            markDirty(world, pos, state);
            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
                if(world instanceof ServerWorld serverWorld) {
                    serverWorld.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                }
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private void craftItem() {
        ItemStack output = new ItemStack(ModItems.HEART, 1);

        this.removeStack(INPUT_SLOT_1, 1);
        this.removeStack(INPUT_SLOT_2, 1);
        this.removeStack(INPUT_SLOT_3, 1);
        this.removeStack(INPUT_SLOT_4, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Item input1 = Items.DIAMOND;
        Item input2 = Items.GLISTERING_MELON_SLICE;
        Item input3 = Items.NETHER_STAR;
        Item input4 = Items.ENCHANTED_GOLDEN_APPLE;
        ItemStack output = new ItemStack(ModItems.HEART, 1);

        return this.getStack(INPUT_SLOT_1).isOf(input1) && this.getStack(INPUT_SLOT_2).isOf(input2) && this.getStack(INPUT_SLOT_3).isOf(input3) && this.getStack(INPUT_SLOT_4).isOf(input4) &&
                canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }
}
