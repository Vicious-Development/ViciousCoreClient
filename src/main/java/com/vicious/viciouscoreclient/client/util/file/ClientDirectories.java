package com.vicious.viciouscoreclient.client.util.file;

import com.vicious.viciouscore.common.util.file.Directories;
import com.vicious.viciouscore.common.util.file.FileUtil;

import java.nio.file.Path;

public class ClientDirectories {
    public static Path viciousCoreClientConfigPath;
    public static Path itemRenderOverridesDirectory;

    public static void initializeConfigDependents() {
        itemRenderOverridesDirectory = FileUtil.createDirectoryIfDNE(Directories.directorize(Directories.viciousResourcesDirectory.toAbsolutePath().toString(),"itemrenderoverrides"));
        viciousCoreClientConfigPath = Directories.directorize(Directories.viciousConfigDirectory.toAbsolutePath().toString(),"coreclient.json");
    }
    public static Path directorize(String dir, String path) {
        return Directories.directorize(dir,path);
    }

    public static String rootDir() {
        return Directories.rootDir();
    }
}
