package dev.callumherr.modding.ducky_lib;

import com.mojang.logging.LogUtils;
import dev.callumherr.modding.ducky_lib.entity.ModEntities;
import dev.callumherr.modding.ducky_lib.entity.client.GolemRenderer;
import dev.callumherr.modding.ducky_lib.entity.client.ModModelLayerThing;
import dev.callumherr.modding.ducky_lib.entity.client.model.GolemModel;
import dev.callumherr.modding.ducky_lib.fluids.test.FluidTypes;
import dev.callumherr.modding.ducky_lib.fluids.test.Fluids;
import dev.callumherr.modding.ducky_lib.fluids.test.ModBlocks;
import dev.callumherr.modding.ducky_lib.gson.JsonLoader;
import dev.callumherr.modding.ducky_lib.init.BlockEntityInit;
import dev.callumherr.modding.ducky_lib.init.BlockInit;
import dev.callumherr.modding.ducky_lib.init.ItemInit;
import dev.callumherr.modding.ducky_lib.utils.Instances;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.CreativeModeTabRegistry;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(DuckyLib.MODID)
public class DuckyLib
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ducky_lib";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public DuckyLib(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::onResourceReload);
        NeoForge.EVENT_BUS.register(this);
        ModEntities.register(modEventBus);
        Fluids.register(modEventBus);
        FluidTypes.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.register(modEventBus);
        BlockEntityInit.register(modEventBus);
        Instances.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
    private void addCreative(final BuildCreativeModeTabContentsEvent event)
    {

    }
    private void onResourceReload(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new JsonLoader());
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(FluidTypes.GOO.get().register(),  FluidTypes.GOO.get());
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.GOLEM.get(), GolemRenderer::new);

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(Fluids.GOOFLOWING.get(), RenderType.TRANSLUCENT);
                ItemBlockRenderTypes.setRenderLayer(Fluids.GOOSTILL.get(), RenderType.TRANSLUCENT);
            });
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayerThing.GOLEM, GolemModel::createBodyLayer);
        }
    }
}
