package dev.callumherr.modding.ducky_lib.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.callumherr.modding.ducky_lib.block.impl.BlockInfo;
import dev.callumherr.modding.ducky_lib.block.impl.Multiblock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger(JsonLoader.class);
    private static final Gson GSON = new Gson();
    private static final String MULTIBLOCK_DIR_PATH = "multiblocks";

    public JsonLoader() {
        super(GSON, MULTIBLOCK_DIR_PATH);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager resourceManager, ProfilerFiller profiler) {
        ClientLevel level = Minecraft.getInstance().level;
        if(level == null)
        {
            return;
        }

        RegistryAccess registryAccess = level.registryAccess();

        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            JsonElement element = entry.getValue();
            LOGGER.info("Processing resource: " + resourceLocation);

            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                Multiblock multiblock = parseMultiblock(jsonObject, registryAccess);
                if (multiblock != null) {
                    LOGGER.info("Loaded multiblock: " + multiblock.name());
                }
            }
        }
    }


    public static List<Multiblock> loadMultiblocks(Level world, ResourceManager resourceManager, String modId) {
        List<Multiblock> multiblocks = new ArrayList<>();
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(modId, MULTIBLOCK_DIR_PATH);

        try {
            List<Resource> resources = resourceManager.getResourceStack(resourceLocation);
            for (Resource resource : resources) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                    JsonObject json = GSON.fromJson(reader, JsonObject.class);
                    Multiblock multiblock = parseMultiblock(json, world.registryAccess());

                    if (multiblock != null) {
                        multiblocks.add(multiblock);
                        LOGGER.info("Loaded multiblock: " + multiblock.name());
                    } else {
                        LOGGER.error("Failed to parse multiblock from JSON: " + json);
                    }
                } catch (IOException e) {
                    LOGGER.error("Failed to load multiblock from resource: " + resourceLocation, e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to access resources for multiblocks: " + resourceLocation, e);
        }

        return multiblocks;
    }

    private static Multiblock parseMultiblock(JsonObject json, RegistryAccess registryAccess) {
        String name = json.get("name").getAsString();
        LOGGER.info("Parsing multiblock: " + name);

        JsonArray size = json.get("size").getAsJsonArray();
        int width = size.get(0).getAsInt();
        int height = size.get(1).getAsInt();
        int depth = size.get(2).getAsInt();

        List<BlockInfo> blockInfos = new ArrayList<>();
        JsonArray blocks = json.get("blocks").getAsJsonArray();
        for (JsonElement element : blocks) {
            JsonObject blockJson = element.getAsJsonObject();
            JsonArray posArray = blockJson.get("position").getAsJsonArray();
            BlockPos pos = new BlockPos(posArray.get(0).getAsInt(), posArray.get(1).getAsInt(), posArray.get(2).getAsInt());
            String blockName = blockJson.get("block").getAsString();

            ResourceLocation blockRes = ResourceLocation.tryParse(blockName);
            Block block = registryAccess.registryOrThrow(Registries.BLOCK).get(blockRes);
            if (block == null) {
                LOGGER.error("Block not found: " + blockName);
                break;
            }

            LOGGER.info("Adding block info: " + block.getName().getString() + " at position: " + pos);
            blockInfos.add(new BlockInfo(pos, block));
        }

        return new Multiblock(name, width, height, depth, blockInfos);
    }
}
