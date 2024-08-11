package dev.callumherr.modding.ducky_lib.init;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit
{
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(DuckyLib.MODID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
