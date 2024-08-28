package dev.callumherr.modding.ducky_lib.entity.example.client;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.utils.Identifier;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayerThing {
    public static final ModelLayerLocation GOLEM = new ModelLayerLocation(
            Identifier.of(DuckyLib.MODID, "golem"), "main"
    );
}
