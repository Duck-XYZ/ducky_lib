package dev.callumherr.modding.ducky_lib.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class DkyDamageUtils {
    public static DamageSource source(LevelReader level, ResourceKey<DamageType> damageType) {
        return source(level, damageType, null, null, null);
    }

    public static DamageSource source(LevelReader level, ResourceKey<DamageType> damageType, @Nullable Entity sourceEntity) {
        return source(level, damageType, sourceEntity, null, null);
    }


    public static DamageSource source(LevelReader level, ResourceKey<DamageType> damageType, @Nullable Entity sourceEntity, @Nullable Entity targetEntity) {
        return source(level, damageType, sourceEntity, targetEntity, null);
    }


    public static DamageSource source(LevelReader level, ResourceKey<DamageType> damageType, @Nullable Entity sourceEntity, @Nullable Entity targetEntity, @Nullable Vec3 damageSourcePos) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(damageType), sourceEntity, targetEntity, damageSourcePos);
    }
}
