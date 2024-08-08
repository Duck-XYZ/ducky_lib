package dev.callumherr.modding.ducky_lib.fluids.test;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Fluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, DuckyLib.MODID);

    public static final DeferredHolder<Fluid, Fluid> GOOFLOWING = FLUIDS.register("goo_flowing",
            () -> new BaseFlowingFluid.Flowing(Fluids.GOOPROPS));

    public static final DeferredHolder<Fluid, FlowingFluid> GOOSTILL = FLUIDS.register("goo_still",
            () -> new BaseFlowingFluid.Source(Fluids.GOOPROPS));

    private static final BaseFlowingFluid.Properties GOOPROPS = new BaseFlowingFluid.Properties(
            FluidTypes.GOO, GOOSTILL, GOOFLOWING).slopeFindDistance(2).block(ModBlocks.GOO);

    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }
}
