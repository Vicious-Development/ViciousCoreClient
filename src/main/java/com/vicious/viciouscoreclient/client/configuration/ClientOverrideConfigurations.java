package com.vicious.viciouscoreclient.client.configuration;

import net.minecraft.client.model.*;
import net.minecraft.item.Item;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ClientOverrideConfigurations {
    protected final static Map<Class<?>, int[]> unreadableArrayLengths = new HashMap<>();
    protected final static Map<String, HeldItemOverrideCFG> heldItemRenderConfigurationsMap = new HashMap<>();
    static{
        putArrayLengths(ModelSilverfish.class, 7, 3);
        putArrayLengths(ModelWither.class, 3, 3);
        putArrayLengths(ModelEnderMite.class, 4);
        putArrayLengths(ModelBlaze.class, 12);
        putArrayLengths(ModelGhast.class,9);
    }
    public static void putArrayLengths(Class<?> clazz, int... lengths){
        ClientOverrideConfigurations.unreadableArrayLengths.put(clazz,lengths);
    }
    public static HeldItemOverrideCFG createWhenHeldOverride(Item in){
        HeldItemOverrideCFG cfg = new HeldItemOverrideCFG(in);
        heldItemRenderConfigurationsMap.putIfAbsent(in.getRegistryName().toString(), cfg);
        return cfg;
    }
    public static HeldItemOverrideCFG createWhenHeldOverride(Item in, Function<Path,ItemTransformOverrideCFG> cfgConstructor){
        HeldItemOverrideCFG cfg = new HeldItemOverrideCFG(in,cfgConstructor);
        heldItemRenderConfigurationsMap.putIfAbsent(in.getRegistryName().toString(), cfg);
        return cfg;
    }

    public static HeldItemOverrideCFG getWhenHeldOverride(Item in){
        return heldItemRenderConfigurationsMap.get(in.getRegistryName().toString());
    }
}
