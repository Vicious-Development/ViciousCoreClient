package com.vicious.viciouscoreclient.client.util.resources;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.OBJParser;
import net.minecraft.util.ResourceLocation;

public class ViciousClientLoader {
    public static CCModel loadModel(ResourceLocation res){
        return CCModel.combine(OBJParser.parseModels(res).values());
    }
    public static CCModel loadViciousModel(String resourcePath){
        return loadModel(getViciousClientResource("models/" + resourcePath));
    }
    public static ResourceLocation getViciousTexture(String resourcePath) {
        return new ResourceLocation("viciouscoreclient:textures/" + resourcePath);
    }

    public static ResourceLocation getViciousModelTexture(String resourcePath) {
        return getViciousTexture("models/" + resourcePath);
    }
    public static ResourceLocation getViciousClientResource(String resourcePath) {
        return new ResourceLocation("viciouscoreclient:" + resourcePath);
    }
}
