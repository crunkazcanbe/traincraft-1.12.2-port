package net.minecraftforge.client.model;

import net.minecraft.util.ResourceLocation;

public class AdvancedModelLoader {
    public static IModelCustom loadModel(ResourceLocation location) {
        try {
            return new WavefrontObject(location);
        } catch (Throwable t) {
            // Never let a bad/missing model abort TESR init; fall back to a no-op model.
            System.err.println("[Traincraft] Failed to load OBJ model " + location + ": " + t);
            return new IModelCustom() {
                public void renderAll() {}
                public void renderPart(String partName) {}
            };
        }
    }
}
