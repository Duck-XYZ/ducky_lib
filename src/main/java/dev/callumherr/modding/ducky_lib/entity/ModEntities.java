package dev.callumherr.modding.ducky_lib.entity;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.entity.custom.GolemEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.http.client.entity.EntityBuilder;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, DuckyLib.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<GolemEntity>> GOLEM = ENTITIES.register("golem",
            () -> EntityType.Builder.of(GolemEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 2f).build("golem"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}