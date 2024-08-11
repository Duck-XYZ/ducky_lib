package dev.callumherr.modding.ducky_lib.init;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityInit
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DuckyLib.MODID);



    private static <T> DeferredRegister<T> makeDeferredRegister(ResourceKey<Registry<T>> registryKey)
    {
        DeferredRegister<T> register = DeferredRegister.create(registryKey, DuckyLib.MODID);
        return register;
    }

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
