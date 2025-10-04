pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.minecraftforge.net/") }
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
        maven { url = uri("https://maven.neoforged.net/releases/") }
    }
}

plugins {
    id("com.possible-triangle.packwiz") version ("0.3.0")
}
