package dev.callumherr.modding.ducky_lib.block.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper Class for our library to make multiblock formation easier.<br>
 * Handles formation and checks blocks in area;
 */
public class MultiBlockHelper
{
    /**
     * Checks if a multi-block structure can form by verifying that all blocks in a 3x3x3
     * area around the specified core position either match a given block tag or are
     * located at a specific placement position.
     *
     * @param levelAccessor the world or level where the blocks are located.
     * @param corePos the central position of the 3x3x3 area.
     * @param placePos the specific position within the area that is allowed to differ.
     * @param tag the tag that all blocks in the area (except the placement position) must match.
     * @return {@code true} if all blocks in the area match the conditions; {@code false} otherwise.
     */
    public static boolean canMultiBlockForm(LevelAccessor levelAccessor, BlockPos corePos, BlockPos placePos, TagKey<Block> tag)
    {
        return get3x3CubesInArea(corePos)
                .allMatch(pos -> pos.equals(placePos)  || levelAccessor.getBlockState(pos).is(tag));
    }

    /**
     * Gets a stream of {@link BlockPos} objects representing all block positions
     * in a 3x3x3 cube centered around the specified position.
     *
     * <p>This method returns a stream of block positions within the volume
     * from {@code (pos.x - 1, pos.y - 1, pos.z - 1)} to
     * {@code (pos.x + 1, pos.y + 1, pos.z + 1)} inclusive.</p>
     *
     * @param pos the central {@link BlockPos} around which the 3x3x3 area is defined.
     * @return a {@link Stream} of {@link BlockPos} within the specified 3x3x3 area.
     */
    public static Stream<BlockPos> get3x3CubesInArea(BlockPos pos)
    {
        return BlockPos.betweenClosedStream(pos.offset(-1,-1,-1),pos.offset(1,1,1));
    }

    /**
     * Checks if a player has the necessary permissions to create a multi-block structure.
     *
     * <p>This method creates block snapshots from the provided block positions and block states,
     * then posts an {@link BlockEvent.EntityMultiPlaceEvent} to the event bus. If the event is canceled,
     * the player does not have permission to create the multi-block structure.</p>
     *
     * @param key the {@link ResourceKey} representing the dimension where the action is taking place.
     * @param world the {@link LevelAccessor} instance representing the world or level.
     * @param pairs a list of {@link Pair} objects, each containing a {@link BlockPos} and its corresponding {@link BlockState}.
     * @param placedAgainst the {@link BlockState} that the multi-block structure is being placed against.
     * @param entity the {@link Entity} attempting to create the multi-block structure.
     * @return {@code true} if the player has permission to create the multi-block structure; {@code false} if the event is canceled.
     */
    public static boolean doesPlayerHasPermsToMakeMultiBlock(ResourceKey<Level> key, LevelAccessor world, List<Pair<BlockPos, BlockState>> pairs, BlockState placedAgainst, Entity entity)
    {
        List<BlockSnapshot> snapshots = pairs.stream()
                .map(pair -> BlockSnapshot.create(key, world, pair.getFirst()))
                .collect(Collectors.toList());
        BlockEvent.EntityMultiPlaceEvent event = new BlockEvent.EntityMultiPlaceEvent(snapshots, placedAgainst, entity);
        NeoForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }
}
