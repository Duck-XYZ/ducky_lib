package dev.callumherr.modding.ducky_lib.utils;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class DkyDamageUtils {
    private static Registry<DamageType> damageTypes;

    public static void init(RegistryAccess register) {
        damageTypes = register.registryOrThrow(Registries.DAMAGE_TYPE);
    }

    /**
     * Gets a damage source from key
     * @param damageType The damage type to get as a source
     * @return Damage source corresponding to damage type
     */
    public static DamageSource source(ResourceKey<DamageType> damageType) {
        return new DamageSource(damageTypes.getHolderOrThrow(damageType));
    }

    /**
     * Gets a damage source from key
     * @param damageType The Damage type to get as a source
     * @param sourceEntity The entity dealing the damage
     * @return Damage source corresponding to Damage type
     */
    public static DamageSource source(ResourceKey<DamageType> damageType, @Nullable Entity sourceEntity) {
        return new DamageSource(damageTypes.getHolderOrThrow(damageType), sourceEntity);
    }

    /**
     * Gets a damage source from key
     * @param damageType The damage type to get as a source
     * @param sourceEntity The entity dealing the damage
     * @param targetEntity The entity taking damage
     * @return Damage source corresponding to damage type
     */
    public static DamageSource source(ResourceKey<DamageType> damageType, @Nullable Entity sourceEntity, @Nullable Entity targetEntity) {
        return new DamageSource(damageTypes.getHolderOrThrow(damageType), sourceEntity, targetEntity);
    }
}
