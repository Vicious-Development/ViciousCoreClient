package com.vicious.viciouscoreclient.client;

import com.vicious.viciouscoreclient.client.util.file.ClientDirectories;
import com.vicious.viciouslib.configuration.ConfigurationValue;
import com.vicious.viciouslib.configuration.JSONConfig;

import java.nio.file.Path;

public class VCoreClientConfig extends JSONConfig {
    private static VCoreClientConfig instance;
    public ConfigurationValue<Boolean> debug = add(new ConfigurationValue<>("DebugSettingsOn", ()->false, this).modifyOnRuntime(true).description("Enable Debug?"));
    public static VCoreClientConfig getInstance(){
        if(instance == null) instance = new VCoreClientConfig(ClientDirectories.viciousCoreClientConfigPath);
        return instance;
    }
    public VCoreClientConfig(Path f) {
        super(f);
    }
}
