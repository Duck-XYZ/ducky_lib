package dev.callumherr.modding.ducky_lib.datagen;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = DuckyLib.MODID,bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        event.getGenerator().addProvider(event.includeServer(),new BlockStatesProvider(packOutput,existingFileHelper));
    }
}
