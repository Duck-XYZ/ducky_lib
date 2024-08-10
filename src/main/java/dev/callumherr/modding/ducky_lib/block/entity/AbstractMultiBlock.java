package dev.callumherr.modding.ducky_lib.block.entity;

import dev.callumherr.modding.ducky_lib.block.entity.enums.MultiBlockStates;
import dev.callumherr.modding.ducky_lib.block.impl.IMultiBlock;
import net.minecraft.world.level.block.Block;


public abstract class AbstractMultiBlock extends Block implements IMultiBlock {

    private MultiBlockStates currentState;
    private MultiBlockStates lastState;

    public AbstractMultiBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void markDirtyAndSync() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public boolean isCorrectSize() {
        return false;
    }

    @Override
    public boolean isBlockAboveValid() {
        return false;
    }
}
