package amber.corwin17.craftablehearts.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class HeartForgeBlockEntityRenderer implements BlockEntityRenderer<HeartForgeBlockEntity> {
    public HeartForgeBlockEntityRenderer(BlockEntityRendererFactory.Context context) { }
    @Override
    public void render(HeartForgeBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        float renderingRotation = entity.getRenderingRotation();
        
        ItemStack itemStack = entity.getStack(0);
        matrices.push();
        matrices.translate(0.15f, 1.3f, 0.15f);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderingRotation));
        
        itemRenderer.renderItem(itemStack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
        
        itemStack = entity.getStack(1);
        matrices.push();
        matrices.translate(0.15f, 1.3f, 0.85f);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderingRotation));
        
        itemRenderer.renderItem(itemStack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
        
        itemStack = entity.getStack(2);
        matrices.push();
        matrices.translate(0.85f, 1.3f, 0.85f);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderingRotation));
        
        itemRenderer.renderItem(itemStack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
        
        itemStack = entity.getStack(3);
        matrices.push();
        matrices.translate(0.85f, 1.3f, 0.15f);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderingRotation));
        
        itemRenderer.renderItem(itemStack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
        
        itemStack = entity.getStack(4);
        matrices.push();
        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRenderingRotation()));
        
        itemRenderer.renderItem(itemStack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }
    
    private int getLightLevel(World world, BlockPos pos){
        int bLight =  world.getLightLevel(LightType.BLOCK, pos);
        int sLight =  world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
