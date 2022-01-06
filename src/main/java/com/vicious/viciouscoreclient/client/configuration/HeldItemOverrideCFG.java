package com.vicious.viciouscoreclient.client.configuration;

import com.vicious.viciouscore.common.configuration.IOverrideConfiguration;
import com.vicious.viciouscore.common.util.file.FileUtil;
import com.vicious.viciouscoreclient.client.util.file.ClientDirectories;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Powerful class designed to allow us to save and load models and render override settings while in game. This means we can edit the rendering of entities and such
 * while in game without needing to restart. It also grants other modders control over our renders.
 */
@SuppressWarnings("unchecked")
public class HeldItemOverrideCFG implements IOverrideConfiguration {
    private final Path PATH;
    private final ItemTransformOverrideCFG ITEM;
    private final Map<Class<? extends ModelBase>, EntityModelOverrideCFG<?>> MOBMAP = new HashMap<>();
    public HeldItemOverrideCFG(Item in) {
        String itemName = in.getRegistryName().toString().replaceAll(":","-");
        PATH = ClientDirectories.directorize(ClientDirectories.itemRenderOverridesDirectory.toAbsolutePath().toString(), itemName);
        FileUtil.createDirectoryIfDNE(PATH);
        ITEM = new ItemTransformOverrideCFG(ClientDirectories.directorize(PATH.toAbsolutePath().toString(), itemName + ".json"));
    }
    public HeldItemOverrideCFG(Item in, Function<Path,ItemTransformOverrideCFG> cfgConstructor) {
        String itemName = in.getRegistryName().toString().replaceAll(":","-");
        PATH = ClientDirectories.directorize(ClientDirectories.itemRenderOverridesDirectory.toAbsolutePath().toString(), itemName);
        FileUtil.createDirectoryIfDNE(PATH);
        ITEM = cfgConstructor.apply(ClientDirectories.directorize(PATH.toAbsolutePath().toString(), itemName + ".json"));
    }

    public static void saveAll() {
        ClientOverrideConfigurations.heldItemRenderConfigurationsMap.forEach((name, cfg)->{
            cfg.ITEM.save();
            cfg.MOBMAP.forEach((mob,override)->{
                override.saveAll();
            });
        });
    }

    public static void readAll() {
        ClientOverrideConfigurations.heldItemRenderConfigurationsMap.forEach((name, cfg) -> {
            cfg.ITEM.readFromJSON();
            cfg.MOBMAP.forEach((mob, override) -> {
                override.readAll();
            });
            cfg.ITEM.save();
        });
    }

    public static void read(String itemname) {
        HeldItemOverrideCFG cfg = ClientOverrideConfigurations.heldItemRenderConfigurationsMap.get(itemname);
        if(cfg == null) return;
        cfg.ITEM.readFromJSON();
        cfg.MOBMAP.forEach((mob, override) -> {
            override.readAll();
        });
    }

    public <T extends ModelBase> HeldItemOverrideCFG addEntityModelOverrider(Class<T> in){
        if(!ClientOverrideConfigurations.unreadableArrayLengths.containsKey(in)) {
            EntityModelOverrideCFG<T> modelconfigurator = new EntityModelOverrideCFG<T>(ClientDirectories.directorize(PATH.toAbsolutePath().toString(), in.getCanonicalName().replaceAll("\\.", "-")), in);
            MOBMAP.putIfAbsent(in, modelconfigurator);
        }
        else{
            EntityModelOverrideCFG<T> modelconfigurator = new EntityModelOverrideCFG<T>(ClientDirectories.directorize(PATH.toAbsolutePath().toString(), in.getCanonicalName().replaceAll("\\.", "-")), in, ClientOverrideConfigurations.unreadableArrayLengths.get(in));
            MOBMAP.putIfAbsent(in, modelconfigurator);
        }
        return this;
    }
    public <T extends ModelBase> EntityModelOverrideCFG<T> getEntityModelConfig(T in){
        return (EntityModelOverrideCFG<T>) MOBMAP.get(in.getClass());
    }
    public <T extends ModelBase> EntityModelOverrideCFG<T> getEntityModelConfig(Class<T> in){
        return (EntityModelOverrideCFG<T>) MOBMAP.get(in);
    }
    public ItemTransformOverrideCFG getItemConfig(){
        return ITEM;
    }

    public static void copyFromResources(String modid, Class<?> mainClass){
        FileUtil.copyResources(mainClass, "/assets/" + modid + "/itemrenderoverrides", ClientDirectories.itemRenderOverridesDirectory.toAbsolutePath().toString());
    }
}
