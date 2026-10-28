pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.4")
    id("com.possible-triangle.packwiz") version ("1.4.+")
}

includeBuild("buildLogic")

include("combined")

file("modules").listFiles().forEach {
    include(":${it.name}")
    project(":${it.name}").projectDir = it
}
