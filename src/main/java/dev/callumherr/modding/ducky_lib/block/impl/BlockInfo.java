package dev.callumherr.modding.ducky_lib.block.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class BlockInfo
{
    private final BlockPos position;
    private final Block block;

    public BlockInfo(BlockPos position, Block block) {
        this.position = position;
        this.block = block;
    }

    public BlockPos getPosition() {
        return position;
    }

    public Block getBlock() {
        return block;
    }
}
