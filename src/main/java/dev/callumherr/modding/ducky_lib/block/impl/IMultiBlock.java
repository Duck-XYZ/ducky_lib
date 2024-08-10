package dev.callumherr.modding.ducky_lib.block.impl;

/**
 * Interface representing a multiblock structure in Minecraft.
 * Multiblock structures are complex blocks made up of multiple smaller blocks.
 * This interface defines the essential methods for managing and interacting with such structures.
 */
public interface IMultiBlock {

    /**
     * Called when the content or state of the multiblock structure changes.
     * Implementations should handle updating the internal state or triggering any necessary
     * actions when the structure is altered.
     */
    void onContentChanged();

    /**
     * Checks if the multiblock structure has the correct size.
     * This method is used to validate whether the multiblock has been correctly formed according to
     * its design specifications.
     *
     * @return true if the multiblock structure is of the correct size, false otherwise.
     */
    boolean isCorrectSize();

    /**
     * Checks if the block directly above this block in the multiblock structure is valid.
     * This method can be used to validate the integrity of the multiblock, ensuring that
     * the blocks are correctly positioned.
     *
     * @return true if the block above is valid, false otherwise.
     */
    boolean isBlockAboveValid();

    /**
     * Performs any necessary operations on each tick.
     * This method should contain the logic that needs to be executed regularly, such as
     * updating the state of the multiblock or handling time-based events.
     */
    void tick();

    /**
     * Marks the multiblock structure as dirty and synchronizes its state.
     * This method should be called whenever the state of the multiblock needs to be updated and
     * communicated to the client or saved to disk. "Marking dirty" typically refers to
     * flagging the block/entity for a data update, while "syncing" ensures that this data is sent
     * to the client or persisted.
     */
    void markDirtyAndSync();
}
