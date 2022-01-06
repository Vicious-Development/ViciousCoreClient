package com.vicious.viciouscoreclient.client.util.file;

import com.vicious.viciouscore.common.util.file.FileUtil;
import com.vicious.viciouscore.common.util.file.ViciousDirectories;

import java.nio.file.Path;

public class ClientDirectories {
    public static Path viciousCoreClientConfigPath;
    public static Path itemRenderOverridesDirectory;

    public static void initializeConfigDependents() {
        itemRenderOverridesDirectory = FileUtil.createDirectoryIfDNE(ViciousDirectories.directorize(ViciousDirectories.viciousResourcesDirectory.toAbsolutePath().toString(),"itemrenderoverrides"));
        viciousCoreClientConfigPath = ViciousDirectories.directorize(ViciousDirectories.viciousConfigDirectory.toAbsolutePath().toString(),"coreclient.json");
    }
    public static Path directorize(String dir, String path) {
        return ViciousDirectories.directorize(dir,path);
    }

    public static String rootDir() {
        return ViciousDirectories.rootDir();
    }
}
