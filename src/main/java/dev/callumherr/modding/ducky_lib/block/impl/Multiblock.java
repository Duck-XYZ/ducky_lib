package dev.callumherr.modding.ducky_lib.block.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class Multiblock
{
    private final String name;
    private final int width;
    private final int height;
    private final int depth;
    private final List<BlockInfo> blocks;

    public Multiblock(String name, int width, int height, int depth, List<BlockInfo> blocks) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.blocks = blocks;
    }

    public boolean checkMultiblock(Level world, BlockPos origin) {
        for (BlockInfo info : blocks) {
            BlockPos relativePos = origin.offset(info.getPosition());
            Block blockAtPosition = world.getBlockState(relativePos).getBlock();
            if (blockAtPosition != info.getBlock()) {
                return false;
            }
        }
        return true;
    }

    public void onMultiblockFormed(Level world, BlockPos origin) {
        world.players().forEach(player -> player.sendSystemMessage(Component.literal("Multiblock formed!")));
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public List<BlockInfo> getBlocks() {
        return blocks;
    }
}
