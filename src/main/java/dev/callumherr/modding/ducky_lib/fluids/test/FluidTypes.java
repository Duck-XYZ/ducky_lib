package dev.callumherr.modding.ducky_lib.fluids.test;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.fluids.DkyFluidType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class FluidTypes {
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister
            .create(NeoForgeRegistries.FLUID_TYPES, DuckyLib.MODID);

    public static final DeferredHolder<FluidType, DkyFluidType> GOO =
            DkyFluidType.Builder.create("goo")
                    .setEntityEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1))
                    .addItemReplacement(Items.GOLD_INGOT, new ItemStack(Items.GOLD_BLOCK))
                    .setFogColor(new Vector3f(224f / 255f, 56f / 255f, 208f / 255f))
                    .buildHolder(TYPES);

    public static void register(IEventBus bus) {
        TYPES.register(bus);
    }
}
