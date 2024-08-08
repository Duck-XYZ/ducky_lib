package dev.callumherr.modding.ducky_lib.utils;

import net.minecraft.resources.ResourceLocation;

/**
 * I HATE RESOURCELOCATION#FROMNAMESPACEANDPATH
 */
public class Identifier {

    /**
     * Creates a ResourceLocation from namespace and path
     * @param namespace the namespace (modid) of the resource
     * @param path the path to the resource
     * @return ResourceLocation pointing to the namespace and path
     */
    public static ResourceLocation of(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    /**
     * Creates a ResourceLocation with default namespace of a set path
     * @param path path to the resource to use
     * @return ResourceLocation with the namespace "minecraft" and given path
     */
    public static ResourceLocation of(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
