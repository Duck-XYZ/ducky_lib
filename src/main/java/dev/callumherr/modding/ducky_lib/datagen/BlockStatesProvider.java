package dev.callumherr.modding.ducky_lib.datagen;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.data.providers.MultiBlockBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStatesProvider extends BlockStateProvider {
    public BlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DuckyLib.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        new MultiBlockBuilder(DuckyLib.MODID)
                .add(1,1,1, Blocks.COBBLESTONE)
                .add(0,0,0, Blocks.COBBLESTONE)
                .add(1,0,0, Blocks.COBBLESTONE)
                .add(0,1,1, Blocks.COBBLESTONE)
                .add(1,0,1, Blocks.COBBLESTONE)
                .add(1,1,1, Blocks.COBBLESTONE)
                .add(1,1,1, Blocks.COBBLESTONE)
                .add(1,1,1, Blocks.COBBLESTONE)
                .add(1,1,1, Blocks.COBBLESTONE)
                .add(1,1,1, Blocks.COBBLESTONE)
        ;
    }
}
