# ViciousCoreClient
Vicious Core is a powerful mod designed to eliminate the tediousness of many processes in modding Minecraft while keeping everything as server efficient as possible. 
##This is the Client module for ViciousCore

For Common Features, see [ViciousCore](https://github.com/Vicious-MCModding/ViciousCore). which is not dependent on CodeChickenLib.
## Client Features:
- Rendering and animation
- Entity rendering overwriting.
## Common Features:
- Mobspawn detection
- Tile Entity Code overwriting
- Field Code overwriting
- Easy Config Creation
## WIP Features:
- Structure Generation
- Tile Entities and MultiBlocks
- GUI Systems.

# Using as a Gradle Dependency
1. Put this in your build script
```gradle
repositories {
    maven {
        name = "ViciousCoreClient"
        url = uri("https://maven.pkg.github.com/Vicious-MCModding/ViciousCoreClient")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.key") ?: System.getenv("GPR_API_KEY")
        }
    }
}
dependencies {
    compile "com.vicious:viciouscoreclient:VERSION"
    //This will also automatically install viciouscore
}
```
2. If you don't have a GPR Key you need to make one. To do so, go here: [https://github.com/settings/tokens](https://github.com/settings/tokens)
* Click generate new token
* Click the read:packages checkbox
* Scroll down and generate that boyo. Copy the key, and in gradle.properties write
```
gpr.user=YOUR GITHUB USERNAME
gpr.key=THE KEY YOU JUST MADE.
```
* You should be good now BUT be warned. This key grants anyone with it special privileges (the ones you gave it in that checkbox section). Make sure that this key is kept private, you'll want your git system to ignore the gradle.properties file in this case. You could also just use the System Environment variables as well.

Doing this will both provide you ALL dependencies for client and any of the dependencies for the dependencies (Wow its like magic).
