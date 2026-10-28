plugins {
    id("com.possible-triangle.core")
    id("net.kyori.blossom")
}

repositories {
    nexus {
        content {
            includeGroup("com.possible-triangle")
            includeGroup("com.rosemods")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
            includeGroupAndSubgroups("net.mezzdev")
            includeGroup("foundry.veil")
            includeGroup("gg.moonflower")
            includeGroup("io.github.ocelot")
        }
    }
    maven {
        url = uri("https://mvn.devos.one/snapshots")
        content {
            includeGroup("com.tterrag.registrate")
        }
    }
    maven {
        url = uri("https://maven.createmod.net")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("net.createmod.ponder")
            includeGroup("dev.engine-room.flywheel")
        }
    }
    maven {
        url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        content {
            includeGroup("fuzs.forgeconfigapiport")
        }
    }
    maven {
        url = uri("https://maven.ryanhcode.dev/releases")
        content {
            includeGroupAndSubgroups("dev.eriksonn")
            includeGroupAndSubgroups("dev.ryanhcode")
            includeGroupAndSubgroups("dev.simulated_team")
        }
    }
}

upload.maven {
    nexus()
    githubPackages()
}
