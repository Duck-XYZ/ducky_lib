package dev.callumherr.modding.ducky_lib.block.entity;

import dev.callumherr.modding.ducky_lib.block.helper.MultiBlockHelper;
import dev.callumherr.modding.ducky_lib.utils.Instances;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Represents the core block entity for a multi-block structure.
 *
 * <p>This abstract class is intended to serve as the central block entity in a multi-block structure.
 * It is responsible for handling key functions related to the multi-block structure, such as updating
 * its neighboring blocks when changes occur.</p>
 */
public abstract class AbstractMultiBlockCoreEntity extends BlockEntity
{
    public static final BlockEntityTicker<AbstractMultiBlockCoreEntity> SERVER_TICKER = (level, pos, state, core)->core.serverTick();

    /**
     * Constructs a new {@code AbstractMultiBlockCoreEntity} with the specified type, position, and block state.
     *
     * @param type the {@link BlockEntityType} of this block entity.
     * @param pos the {@link BlockPos} position of this block entity.
     * @param blockState the {@link BlockState} associated with this block entity.
     */
    public AbstractMultiBlockCoreEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract AbstractMultiBlockCoreEntity create(BlockPos pos, BlockState state);
    /**
     * Called to mark this block entity as having been changed.
     *
     * <p>This method not only marks the block entity as changed, but it also updates neighboring blocks
     * within the 3x3x3 area centered around this block entity's position. This is achieved by iterating
     * over the positions in the 3x3x3 area and invoking the {@code updateNeighbourForOutputSignal} method
     * on the neighboring blocks.</p>
     */
    @Override
    public void setChanged() {
        super.setChanged();
        assert this.level != null;

        MultiBlockHelper.get3x3CubesInArea(this.worldPosition)
                .filter(extPos -> extPos.equals(this.worldPosition))
                .forEach(extPos -> this.level.updateNeighbourForOutputSignal(extPos, this.getBlockState().getBlock()));
    }

    protected abstract void serverTick();

}
