package com.vicious.viciouscoreclient.client.util.resources;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.OBJParser;
import com.vicious.viciouscore.common.util.resources.ViciousLoader;
import net.minecraft.util.ResourceLocation;

public class ViciousClientLoader {
    public static CCModel loadModel(ResourceLocation res){
        return CCModel.combine(OBJParser.parseModels(res).values());
    }
    public static CCModel loadViciousModel(String resourcePath){
        return loadModel(ViciousLoader.getViciousResource("models/" + resourcePath));
    }
    public static ResourceLocation getViciousTexture(String resourcePath) {
        return new ResourceLocation("viciouscoreclient:textures/" + resourcePath);
    }

    public static ResourceLocation getViciousModelTexture(String resourcePath) {
        return getViciousTexture("models/" + resourcePath);
    }
}
