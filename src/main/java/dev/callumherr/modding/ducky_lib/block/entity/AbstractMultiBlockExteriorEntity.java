package dev.callumherr.modding.ducky_lib.block.entity;

import dev.callumherr.modding.ducky_lib.block.AbstractMultiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract base class for exterior block entities in a multi-block structure.
 *
 * <p>This class provides the foundation for exterior blocks within a multi-block structure,
 * including methods for retrieving the core block entity of the structure.</p>
 */
public abstract class AbstractMultiBlockExteriorEntity extends BlockEntity {

    /**
     * Constructor for the AbstractMultiBlockExteriorEntity.
     *
     * @param type       The type of the block entity.
     * @param pos        The position of the block entity in the world.
     * @param blockState The state of the block.
     */
    public AbstractMultiBlockExteriorEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    /**
     * Abstract method to create a new instance of this block entity.
     *
     * <p>This method must be implemented by subclasses to instantiate the specific block entity
     * with the given position and block state.</p>
     *
     * @param pos   The position where the block entity will be created.
     * @param state The block state associated with the entity.
     * @return A new instance of this block entity.
     */
    public abstract AbstractMultiBlockExteriorEntity create(BlockPos pos, BlockState state);

    /**
     * Retrieves the core block entity of the multi-block structure that this exterior block entity belongs to.
     *
     * <p>This method checks if the block entity at the calculated core position is an instance of
     * {@link AbstractMultiBlockCoreEntity}. If so, it returns the core entity; otherwise, it returns null.</p>
     *
     * @return The core block entity if it exists, or null if the core block entity is not found.
     */
    @Deprecated
    @Nullable
    public AbstractMultiBlockCoreEntity getCoreTile() {
        Level level = this.level;
        BlockPos thisPos = this.worldPosition;
        BlockState thisState = this.getBlockState();

        // Ensure the current block is part of an AbstractMultiBlock and get the core block entity
        return thisState.getBlock() instanceof AbstractMultiBlock
                && level.getBlockEntity(AbstractMultiBlock.getCorePos(thisState, thisPos)) instanceof AbstractMultiBlockCoreEntity core
                ? core
                : null;
    }
}
