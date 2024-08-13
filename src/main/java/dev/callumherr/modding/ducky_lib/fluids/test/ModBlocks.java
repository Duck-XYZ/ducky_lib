package dev.callumherr.modding.ducky_lib.fluids.test;

import com.google.common.base.Supplier;
import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.init.ItemInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DuckyLib.MODID);

    public static final DeferredBlock<LiquidBlock> GOO = registerBlock("goo_block",
            () -> new LiquidBlock(Fluids.GOOSTILL.get(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
