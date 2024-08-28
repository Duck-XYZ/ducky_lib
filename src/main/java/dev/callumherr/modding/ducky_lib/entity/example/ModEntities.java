package dev.callumherr.modding.ducky_lib.entity.example;

import dev.callumherr.modding.ducky_lib.DuckyLib;
import dev.callumherr.modding.ducky_lib.entity.api.MultiPartMonster;
import dev.callumherr.modding.ducky_lib.entity.example.custom.GolemEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, DuckyLib.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<MultiPartMonster<?>>> GOLEM = ENTITIES.register("golem",
            () -> EntityType.Builder.of(GolemEntity::new, MobCategory.CREATURE)
                    .sized(1.25f, 3f).build("golem"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
