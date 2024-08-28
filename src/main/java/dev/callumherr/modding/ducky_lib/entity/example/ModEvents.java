package dev.callumherr.modding.ducky_lib.entity.example;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.minecraft.world.entity.animal.Sheep;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = DuckyLib.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GOLEM.get(), Sheep.createAttributes().build());
    }
}
