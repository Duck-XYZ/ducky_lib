package dev.callumherr.modding.ducky_lib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for a multi-block structure in Minecraft.
 *
 * <p>This class handles the core functionalities of a multi-block structure, such as state management, rotation,
 * mirroring, and creating block entities. Subclasses must implement methods for creating block entities and
 * handling tick updates.</p>
 */
public abstract class AbstractMultiBlock extends Block implements EntityBlock {

    // Properties representing the relative position within the multi-block structure.
    public static final IntegerProperty X = IntegerProperty.create("x", 0, 2);
    public static final IntegerProperty Y = IntegerProperty.create("y", 0, 2);
    public static final IntegerProperty Z = IntegerProperty.create("z", 0, 2);

    /**
     * Constructor for the AbstractMultiBlock.
     *
     * @param properties The properties for the block, defining its physical behavior and characteristics.
     */
    public AbstractMultiBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(X, 0)
                .setValue(Y, 0)
                .setValue(Z, 0));
    }

    /**
     * Creates the block state definition for this block, including the X, Y, and Z properties.
     *
     * @param builder The builder used to define the block's state properties.
     */
    @Override
    protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(X, Y, Z);
    }

    /**
     * Calculates the position of the core block in the multi-block structure.
     *
     * @param exteriorState The block state of the exterior block.
     * @param exteriorPos   The position of the exterior block.
     * @return The position of the core block.
     */
    public static BlockPos getCorePos(BlockState exteriorState, BlockPos exteriorPos) {
        int xOff = exteriorState.hasProperty(X) ? 1 - exteriorState.getValue(X) : 0;
        int yOff = exteriorState.hasProperty(Y) ? 1 - exteriorState.getValue(Y) : 0;
        int zOff = exteriorState.hasProperty(Z) ? 1 - exteriorState.getValue(Z) : 0;
        return exteriorPos.offset(xOff, yOff, zOff);
    }

    /**
     * Checks if the given block state represents the core of the multi-block structure.
     *
     * @param state The block state to check.
     * @return True if the block state represents the core, false otherwise.
     */
    public boolean isCore(BlockState state) {
        return state.hasProperty(X) && state.hasProperty(Y) && state.hasProperty(Z)
                && state.getValue(X) == 1
                && state.getValue(Y) == 1
                && state.getValue(Z) == 1;
    }

    /**
     * Handles rotation of the block based on the given rotation parameter.
     *
     * @param state The current block state.
     * @param rot   The rotation to apply.
     * @return The new block state after rotation.
     */
    @Deprecated
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (state.hasProperty(X) && state.hasProperty(Z)) {
            int x = state.getValue(X);
            int z = state.getValue(Z);
            switch (rot) {
                case CLOCKWISE_90:
                    return state.setValue(X, 2 - z).setValue(Z, x);
                case CLOCKWISE_180:
                    return state.setValue(X, 2 - x).setValue(Z, 2 - z);
                case COUNTERCLOCKWISE_90:
                    return state.setValue(X, z).setValue(Z, 2 - x);
                default:
                    return state;
            }
        } else {
            return super.rotate(state, rot);
        }
    }

    /**
     * Handles mirroring of the block based on the given mirror parameter.
     *
     * @param state The current block state.
     * @param mirror The mirror to apply.
     * @return The new block state after mirroring.
     */
    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (state.hasProperty(X) && state.hasProperty(Z)) {
            switch (mirror) {
                case LEFT_RIGHT: // mirror across the x-axis (flip Z)
                    return state.setValue(Z, 2 - state.getValue(Z));
                case FRONT_BACK: // mirror across the z-axis (flip X)
                    return state.setValue(X, 2 - state.getValue(X));
                default:
                    return state;
            }
        } else {
            return super.mirror(state, mirror);
        }
    }

    /**
     * Creates a new block entity at the given position and state.
     *
     * <p>This method is abstract and must be implemented by subclasses to return an appropriate block entity
     * for the multi-block structure.</p>
     *
     * @param pos   The position of the block.
     * @param state The block state at the position.
     * @return The new block entity instance.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return createBlockEntity(pos, state);
    }

    /**
     * Abstract method to create a block entity for the multi-block structure.
     *
     * @param pos   The position of the block.
     * @param state The block state at the position.
     * @return The block entity instance.
     */
    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    /**
     * Returns a ticker for the block entity at the given position, if applicable.
     *
     * <p>This method is abstract and must be implemented by subclasses to return an appropriate ticker
     * for the multi-block structure's block entity.</p>
     *
     * @param level The level in which the block is present.
     * @param state The block state.
     * @param type  The block entity type.
     * @param <T>   The type of block entity.
     * @return The block entity ticker or null if no ticker is required.
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(level, state, type);
    }

    /**
     * Abstract method to create a ticker for the block entity in the multi-block structure.
     *
     * @param level The level in which the block is present.
     * @param state The block state.
     * @param type  The block entity type.
     * @param <T>   The type of block entity.
     * @return The block entity ticker or null if no ticker is required.
     */
    protected abstract <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockState state, BlockEntityType<T> type);

    /**
     * Sends a message to nearby players when the multi-block structure is formed.
     * Not used since debugging is complete. In the future, it may be used.
     * @param pos   The position of the block.
     * @param state The block state.
     */
    private void sendMultiblockFormedMessage(BlockPos pos, BlockState state) {
        Level level = Objects.requireNonNull(this.newBlockEntity(pos, state)).getLevel();

        if (level != null && !level.isClientSide) {
            AABB area = new AABB(pos).inflate(10);
            List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, area);

            for (ServerPlayer player : players) {
                player.sendSystemMessage(Component.literal("Multiblock structure formed!"));
            }
        }
    }
}
