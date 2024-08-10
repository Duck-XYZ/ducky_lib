package dev.callumherr.modding.ducky_lib.fluids.test;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.fluids.DkyFluidType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
                    .setEntityEffect(MobEffects.DAMAGE_BOOST)
                    .addItemReplacement(Items.GOLD_INGOT, new ItemStack(Items.GOLD_BLOCK, 16))
                    .setFogColor(new Vector3f(45f / 255f, 51f / 255f, 214f / 255f))
                    .buildHolder(TYPES);

    public static void register(IEventBus bus) {
        TYPES.register(bus);
    }
}
