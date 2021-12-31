package com.vicious.viciouscoreclient.client.util.resources;

import codechicken.lib.texture.TextureUtils.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class ResourceCache implements IIconRegister, IResourceManagerReloadListener {
    public static ResourceLocation ORBSPRITELOCATION = ViciousClientLoader.getViciousModelTexture("orb.png");
    public static ResourceLocation TESTHEADSPRITELOCATION = ViciousClientLoader.getViciousModelTexture("testhead.png");
    public static ResourceLocation TESTBODYSPRITELOCATION = ViciousClientLoader.getViciousModelTexture("testbody.png");
    public static ResourceLocation TESTLIMBSPRITELOCATION = ViciousClientLoader.getViciousModelTexture("testlimb.png");
    @Override
    public void registerIcons(TextureMap textureMap) {
        //Use in future projects for item icons.
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {}
}
