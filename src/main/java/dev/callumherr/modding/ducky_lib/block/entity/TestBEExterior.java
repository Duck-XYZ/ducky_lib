package dev.callumherr.modding.ducky_lib.block.entity;

import dev.callumherr.modding.ducky_lib.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TestBEExterior extends AbstractMultiBlockExteriorEntity{
    /**
     * Constructor for the AbstractMultiBlockExteriorEntity.
     *
     * @param pos        The position of the block entity in the world.
     * @param blockState The state of the block.
     */
    public TestBEExterior(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.ExteriorBlockEntityType.get(), pos, blockState);
    }

    @Override
    public AbstractMultiBlockExteriorEntity create(BlockPos pos, BlockState state) {
        return new TestBEExterior(pos,state);
    }
}
