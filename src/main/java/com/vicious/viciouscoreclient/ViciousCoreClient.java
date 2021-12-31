package com.vicious.viciouscoreclient;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ResourceUtils;
import com.vicious.viciouscore.ViciousCore;
import com.vicious.viciouscoreclient.client.VCoreClientConfig;
import com.vicious.viciouscoreclient.client.commands.CommandItemModelConfigReload;
import com.vicious.viciouscoreclient.client.configuration.HeldItemOverrideCFG;
import com.vicious.viciouscoreclient.client.registries.RenderRegistry;
import com.vicious.viciouscoreclient.client.render.RenderEventManager;
import com.vicious.viciouscoreclient.client.render.ViciousRenderManager;
import com.vicious.viciouscoreclient.client.util.ClientMappingsInitializer;
import com.vicious.viciouscoreclient.client.util.file.ClientDirectories;
import com.vicious.viciouscoreclient.client.util.resources.ResourceCache;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = ViciousCoreClient.MODID, name = ViciousCoreClient.NAME, version = ViciousCoreClient.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:codechickenlib;required-after:viciouscore")
public class ViciousCoreClient
{
    public static final String MODID = "viciouscoreclient";
    public static final String NAME = "Vicious Core Client";
    public static final String VERSION = "1.0.2";
    public static VCoreClientConfig CFG;
    public static ViciousCoreClient instance;
    static {
        ClientDirectories.initializeConfigDependents();
        CFG = VCoreClientConfig.getInstance();
    }

    public static Logger logger;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        logger = event.getModLog();
        if(!ViciousCore.CFG.firstLoad.getBoolean()) {
            if(event.getSide() == Side.CLIENT) HeldItemOverrideCFG.copyFromResources(MODID,this.getClass());
        }
        if(event.getSide() == Side.CLIENT) {
            clientPreInit(event);
        }
    }

    /**
     * Proxies are stupid. This works completely fine.
     * @param event
     */
    @SideOnly(Side.CLIENT)
    public void clientPreInit(FMLPreInitializationEvent event){
        ClientMappingsInitializer.init();
        RenderRegistry.register();
        MinecraftForge.EVENT_BUS.register(RenderEventManager.class);
        ClientCommandHandler.instance.registerCommand(new CommandItemModelConfigReload());
        //Necessary CCL implementations
        TextureUtils.addIconRegister(new ResourceCache());
        ResourceUtils.registerReloadListener(new ResourceCache());
    }
    @SideOnly(Side.CLIENT)
    public void clientInit(FMLInitializationEvent event){
        new ViciousRenderManager(); //Instance is stored in the VRM.
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(event.getSide() == Side.CLIENT) {
            clientInit(event);
        }
    }
    @EventHandler
    public void onStop(FMLModDisabledEvent event){
        CFG.save();
        HeldItemOverrideCFG.saveAll();
    }
}
