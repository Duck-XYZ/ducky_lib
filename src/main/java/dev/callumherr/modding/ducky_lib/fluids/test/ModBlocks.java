package dev.callumherr.modding.ducky_lib.fluids.test;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DuckyLib.MODID);

    public static final DeferredBlock<LiquidBlock> GOO = BLOCKS.register("goo_block",
            () -> new LiquidBlock(Fluids.GOOSTILL.get(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
