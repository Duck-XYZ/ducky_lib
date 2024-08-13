package dev.callumherr.modding.ducky_lib.data.providers;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class MultiBlockBuilder {

    private final String MOD_ID;
    private final Map<String, JsonObject> variants = new HashMap<>();

    public MultiBlockBuilder(String modId) {
        MOD_ID = modId;
    }

    /**
     * Adds a block to the multi-block structure at specific coordinates.
     *
     * @param x        The x-coordinate.
     * @param y        The y-coordinate.
     * @param z        The z-coordinate.
     * @param block    The block to be used.
     * @param rotation Optional rotation for the block.
     * @return The MultiBlockBuilder instance for chaining.
     */
    public MultiBlockBuilder add(int x, int y, int z, Block block, Integer rotation) {
        String key = "y=" + y + ",x=" + x + ",z=" + z;
        JsonObject modelObject = new JsonObject();
        modelObject.add("model", new JsonPrimitive(getModelPath(block)));

        if (rotation != null) {
            modelObject.add("x", new JsonPrimitive(rotation));
        }

        variants.put(key, modelObject);
        return this;
    }

    public MultiBlockBuilder add(int x, int y, int z, Block block) {
        return add(x, y, z, block, null);
    }

    /**
     * Converts a Block instance to a model path string.
     *
     * @param block The block to convert.
     * @return The model path string for the block.
     */
    private String getModelPath(Block block) {

        return MOD_ID+":block/" + block.getName().getString();
    }

    public JsonObject build() {
        JsonObject result = new JsonObject();
        JsonObject variantsObject = new JsonObject();

        for (Map.Entry<String, JsonObject> entry : variants.entrySet()) {
            variantsObject.add(entry.getKey(), entry.getValue());
        }

        result.add("variants", variantsObject);
        return result;
    }
}
