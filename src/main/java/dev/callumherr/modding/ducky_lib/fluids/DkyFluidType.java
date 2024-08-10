package dev.callumherr.modding.ducky_lib.fluids;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class DkyFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final int tintColor;
    private final Vector3f fogColor;
    private final float fogStart;
    private final float fogEnd;
    private final @Nullable Holder<MobEffect> entityEffect;
    private final int amplifier;
    private final Map<Item, ItemStack> replacementItems;

    private DkyFluidType(final ResourceLocation stillTexture, final ResourceLocation flowingTexture, final ResourceLocation overlayTexture,
                         final int tintColor, final Vector3f fogColor, float fogStart, float fogEnd, final Properties properties, @Nullable Holder<MobEffect> entityEffect, int amplifier, Map<Item, ItemStack> replacementItems) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
        this.entityEffect = entityEffect;
        this.amplifier = amplifier;
        this.replacementItems = replacementItems;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public int getTintColor() {
        return tintColor;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public Vector3f getFogColor() {
        return fogColor;
    }

    public float getFogStart() {
        return fogStart;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public @Nullable MobEffectInstance getEntityEffect() {
        if (entityEffect == null) return null;
        return new MobEffectInstance(entityEffect, 40, amplifier);
    }

    public @Nullable ItemStack getReplacementItem(Item item) {
        return this.replacementItems.get(item);
    }

    public IClientFluidTypeExtensions register() {
        return new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(fogStart);
                RenderSystem.setShaderFogEnd(fogEnd);
            }
        };
    }

    public static class Builder {
        private final String name;
        private ResourceLocation stillTexture;
        private ResourceLocation flowingTexture;
        private ResourceLocation overlayTexture;
        private int tintColor;
        private Vector3f fogColor;
        private Properties properties;
        private float fogStart;
        private float fogEnd;
        private @Nullable Holder<MobEffect> entityEffect;
        private int amplifier;
        private final Map<Item, ItemStack> itemReplacements;

        /**
         * Creates a new builder for a fluid type
         * @param name fluid type identifier
         */
        public Builder(String name) {
            this.name = name;
            this.stillTexture = ResourceLocation.parse("block/water_still");
            this.flowingTexture = ResourceLocation.parse("block/water_flow");
            this.overlayTexture = ResourceLocation.parse("block/water_overlay");
            this.tintColor = 0xA0F4F4F4;
            this.fogColor = new Vector3f(0, 0,0);
            this.properties = Properties.create();
            this.fogStart = 1f;
            this.fogEnd = 3f;
            this.entityEffect = null;
            this.amplifier = 0;
            this.itemReplacements = new HashMap<>();
        }

        public static Builder create(String name) {
            return new Builder(name);
        }

        /**
         * Sets the texture to be used for the fluid while still
         * @param texture still fluid resource location, defaults to water
         * @return this
         */
        public Builder setStillTexture(ResourceLocation texture) {
            this.stillTexture = texture;
            return this;
        }

        /**
         * Sets the texture to be used for the fluid while still
         * @param texture flowing fluid resource location, defaults to water
         * @return this
         */
        public Builder setFlowingTexture(ResourceLocation texture) {
            this.flowingTexture = texture;
            return this;
        }

        /**
         * Sets the texture shown when submerged
         * @param texture overlay resource location, defaults to water
         * @return this
         */
        public Builder setOverlayTexture(ResourceLocation texture) {
            this.overlayTexture = texture;
            return this;
        }

        /**
         * Sets the tint applied to the fluid
         * @param color the color of the tint to apply
         * @return this
         */
        public Builder setTintColor(int color) {
            this.tintColor = color;
            return this;
        }

        /**
         * Set the color that applies to the fog when submerged
         * @param color color of the fog to apply
         * @return this
         */
        public Builder setFogColor(Vector3f color) {
            this.fogColor = color;
            return this;
        }

        /**
         * The properties for the fluid to use
         * @param properties The fluids properties
         * @return this
         * @see net.neoforged.neoforge.fluids.FluidType.Properties
         */
        public Builder setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        /**
         * When the fog starts rendering when submerged
         * @param distance the distance at which the fog starts to appear, default 1F
         * @return this
         */
        public Builder setFogStart(float distance) {
            this.fogStart = distance;
            return this;
        }

        /**
         * The distance the fog goes before blocking any further sight
         * @param distance the distance the fog ends at
         * @return this
         */
        public Builder setFogEnd(float distance) {
            this.fogEnd = distance;
            return this;
        }

        /**
         * Sets an effect to be applied to entities while they are in the fluid
         * @param effect The MobEffect to apply
         * @param amp the amplifier of the mob effect
         * @return this
         */
        public Builder setEntityEffect(Holder<MobEffect> effect, int amp) {
            this.entityEffect = effect;
            this.amplifier = amp;
            return this;
        }

        /**
         * Sets an effect, with an amplifier of 0, to be applied to entities while they are in the fluid
         * @param effect the MobEffect to apply
         * @return this
         */
        public Builder setEntityEffect(Holder<MobEffect> effect) {
            this.entityEffect = effect;
            return this;
        }

        /**
         * Add items that when in the fluid will be replaced by the given item stack
         * @param original The item to replace
         * @param replacement ItemStack to spawn in place of the item
         * @return this
         */
        public Builder addItemReplacement(Item original, ItemStack replacement) {
            this.itemReplacements.put(original, replacement);
            return this;
        }

        /**
         * Registers the fluid using the given register with the identifier given in constructor
         * @param deferredRegister the registery to register the fluid to
         * @return Holder for the fluid type
         */
        public DeferredHolder<FluidType, DkyFluidType> buildHolder(DeferredRegister<FluidType> deferredRegister) {
            return deferredRegister.register(name, this::build);
        }

        /**
         * Creates a new FluidType
         * @return the fluid type with the given parameters
         * @see Builder#buildHolder(DeferredRegister) for registration
         */
        public DkyFluidType build() {
            return new DkyFluidType(
                    stillTexture,
                    flowingTexture,
                    overlayTexture,
                    tintColor,
                    fogColor,
                    fogStart,
                    fogEnd,
                    properties,
                    entityEffect,
                    amplifier,
                    itemReplacements);
        }
    }
}
