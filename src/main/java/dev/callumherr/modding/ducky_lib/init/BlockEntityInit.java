package dev.callumherr.modding.ducky_lib.init;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.block.entity.TestBECore;
import dev.callumherr.modding.ducky_lib.block.entity.TestBEExterior;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityInit
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            makeDeferredRegister(Registries.BLOCK_ENTITY_TYPE);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TestBECore>> CoreBlockEntityType;
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TestBEExterior>> ExteriorBlockEntityType;

    static
    {
        CoreBlockEntityType = BLOCK_ENTITIES.register("multiblock_core",
                () -> BlockEntityType.Builder.of(TestBECore::new, BlockInit.TEST_BLOCK.get()).build(null));

        ExteriorBlockEntityType = BLOCK_ENTITIES.register("multiblock_exterior",
                () -> BlockEntityType.Builder.of(TestBEExterior::new, BlockInit.TEST_BLOCK.get()).build(null));
    }



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
