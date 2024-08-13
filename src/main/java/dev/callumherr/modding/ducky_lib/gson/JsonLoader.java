package dev.callumherr.modding.ducky_lib.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.callumherr.modding.ducky_lib.block.impl.BlockInfo;
import dev.callumherr.modding.ducky_lib.block.impl.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    private static final Gson GSON = new Gson();
    private static final String MULTIBLOCK_DIR = "ducky_lib/config/multiblocks/";

    public static List<Multiblock> loadMultiblocks(Level world) {
        List<Multiblock> multiblocks = new ArrayList<>();
        File directory = new File(MULTIBLOCK_DIR);

        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    try (FileReader reader = new FileReader(file)) {
                        JsonObject json = GSON.fromJson(reader, JsonObject.class);
                        Multiblock multiblock = parseMultiblock(json, world.registryAccess());
                        multiblocks.add(multiblock);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return multiblocks;
    }

    private static Multiblock parseMultiblock(JsonObject json, RegistryAccess registryAccess) {
        String name = json.get("name").getAsString();
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

            ResourceLocation blockRes = ResourceLocation.parse(blockName);
            Block block = registryAccess.registryOrThrow(Registries.BLOCK).get(blockRes);
            if (block == null) {
                System.err.println("Block not found: " + blockName);
                continue;
            }

            blockInfos.add(new BlockInfo(pos, block));
        }

        return new Multiblock(name, width, height, depth, blockInfos);
    }
}
