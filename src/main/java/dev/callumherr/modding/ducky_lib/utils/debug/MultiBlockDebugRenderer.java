package dev.callumherr.modding.ducky_lib.utils.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.callumherr.modding.ducky_lib.block.helper.MultiBlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.stream.Stream;

/**
 * A debug renderer for visualizing 3x3 multi-block areas.
 *
 * <p>This class provides a method to render a visual representation of a 3x3 area
 * centered around a specified block position, useful for debugging multi-block structures.</p>
 */
public class MultiBlockDebugRenderer {

    private final Minecraft client;

    /**
     * Constructs a new MultiBlockDebugRenderer.
     *
     * @param client The Minecraft client instance, used to access the game world and rendering utilities.
     */
    public MultiBlockDebugRenderer(Minecraft client) {
        this.client = client;
    }

    /**
     * Renders a visual box around each block position in a 3x3 area centered on the given block position.
     *
     * <p>This method iterates over each block position within the 3x3 area and draws a bounding box around it,
     * using the specified PoseStack and VertexConsumer for rendering.</p>
     *
     * @param centerPos The central position of the 3x3 area to be rendered.
     * @param poseStack The PoseStack for managing matrix transformations.
     * @param consumer  The VertexConsumer used to render the bounding box.
     */
    public void render3x3Area(BlockPos centerPos, PoseStack poseStack, VertexConsumer consumer) {
        Stream<BlockPos> positions = MultiBlockHelper.get3x3CubesInArea(centerPos);

        positions.forEach(pos -> {
            AABB box = new AABB(pos);

            poseStack.pushPose();
            poseStack.translate(-client.gameRenderer.getMainCamera().getPosition().x,
                    -client.gameRenderer.getMainCamera().getPosition().y,
                    -client.gameRenderer.getMainCamera().getPosition().z);

            // Renders the bounding box using line box.
            LevelRenderer.renderLineBox(poseStack, consumer,
                    box.minX, box.minY, box.minZ,
                    box.maxX, box.maxY, box.maxZ,
                    1.0F, 0.0F, 0.0F, 1.0F);

            poseStack.popPose();
        });
    }
}
