package dev.callumherr.modding.ducky_lib.block.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public record Multiblock(String name, int width, int height, int depth, List<BlockInfo> blocks) {
    private static final Logger LOGGER = LogManager.getLogger(Multiblock.class);

    public boolean checkMultiblock(Level world, BlockPos origin) {
        LOGGER.info("Checking multiblock formation for: " + name);

        for (BlockInfo info : blocks) {
            BlockPos relativePos = origin.offset(info.position());
            Block blockAtPosition = world.getBlockState(relativePos).getBlock();
            LOGGER.info("Expected block: " + info.block().getName().getString() +
                    " at position: " + relativePos +
                    ", Found block: " + blockAtPosition.getName().getString());

            if (blockAtPosition != info.block()) {
                LOGGER.warn("Multiblock formation failed at position: " + relativePos +
                        ", Expected: " + info.block().getName().getString() +
                        ", Found: " + blockAtPosition.getName().getString());
                return false;
            }
        }

        LOGGER.info("Multiblock formation successful for: " + name);
        return true;
    }

    public void onMultiblockFormed(Level world, BlockPos origin) {
        LOGGER.info("Multiblock formed: " + name + " at origin: " + origin);
        world.players().forEach(player -> player.sendSystemMessage(Component.literal("Multiblock formed!")));
    }
}