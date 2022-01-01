package com.vicious.viciouscoreclient;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ResourceUtils;
import com.vicious.viciouscore.ViciousCore;
import com.vicious.viciouscoreclient.client.VCoreClientConfig;
import com.vicious.viciouscoreclient.client.commands.CommandItemModelConfigReload;
import com.vicious.viciouscoreclient.client.configuration.HeldItemOverrideCFG;
import com.vicious.viciouscoreclient.client.registries.RenderRegistry;
import com.vicious.viciouscoreclient.client.render.RenderEventManager;
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
import org.apache.logging.log4j.Logger;

@Mod(modid = ViciousCoreClient.MODID, name = ViciousCoreClient.NAME, version = ViciousCoreClient.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:codechickenlib;required-after:viciouscore")
public class ViciousCoreClient
{
    public static final String MODID = "viciouscoreclient";
    public static final String NAME = "Vicious Core Client";
    public static final String VERSION = "1.1.0";
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

        logger = event.getModLog();
        if(event.getSide() == Side.SERVER){
            logger.warn("ViciousCoreclient is obviously clientsided!" +
                    "\n Running this won't impact performance but may consume more memory." +
                    "\n We recommend deleting the mod from your server entirely." +
                    "\n If you are a pack developer producing a server pack, make server owner's lives easier by removing client sided mods." +
                    "\n Thanks for reading." +
                    "\n (I didn't make this mod clientsided only in order to make this PSA) >:)");
            return;
        }
        instance = this;
        if(event.getSide() == Side.CLIENT) {
            if(!ViciousCore.CFG.firstLoad.getBoolean()) HeldItemOverrideCFG.copyFromResources(MODID,this.getClass());
            ClientMappingsInitializer.init();
            RenderRegistry.register();
            MinecraftForge.EVENT_BUS.register(RenderEventManager.class);
            ClientCommandHandler.instance.registerCommand(new CommandItemModelConfigReload());
            //Necessary CCL implementations
            TextureUtils.addIconRegister(new ResourceCache());
            ResourceUtils.registerReloadListener(new ResourceCache());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    @EventHandler
    public void onStop(FMLModDisabledEvent event){
        CFG.save();
        HeldItemOverrideCFG.saveAll();
    }
}
