package dev.callumherr.modding.ducky_lib.block.entity;

import dev.callumherr.modding.ducky_lib.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBECore extends AbstractMultiBlockCoreEntity{
    /**
     * Constructs a new {@code AbstractMultiBlockCoreEntity} with the specified type, position, and block state.
     *
     * @param pos        the {@link BlockPos} position of this block entity.
     * @param blockState the {@link BlockState} associated with this block entity.
     */
    public TestBECore(BlockPos pos, BlockState blockState) {
        super(BlockEntityInit.CoreBlockEntityType.get(), pos, blockState);
    }

    @Override
    public TestBECore create(BlockPos pos, BlockState state) {
        return new TestBECore(pos,state);
    }

    @Override
    protected void serverTick() {

    }

}
