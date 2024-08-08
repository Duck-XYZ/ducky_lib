package dev.callumherr.modding.ducky_lib.entity.client;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.entity.client.model.GolemModel;
import dev.callumherr.modding.ducky_lib.utils.Identifier;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GolemRenderer extends MobRenderer {
    public GolemRenderer(EntityRendererProvider.Context context) {
        super(context,
                new GolemModel<>(context.bakeLayer(ModModelLayerThing.GOLEM)),
                1f);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return Identifier.of(DuckyLib.MODID, "textures/entity/golem.png");
    }
}
