package dev.callumherr.modding.ducky_lib.block;

import dev.callumherr.modding.ducky_lib.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlock extends AbstractMultiBlock {
    /**
     * Constructor for the AbstractMultiBlock.
     *
     * @param properties The properties for the block, defining its physical behavior and characteristics.
     */
    public TestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return isCore(state) ? BlockEntityInit.CoreBlockEntityType.get().create(pos,state)
                : BlockEntityInit.ExteriorBlockEntityType.get().create(pos,state);
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
