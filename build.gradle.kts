import net.minecraftforge.gradle.common.util.MinecraftExtension

val mod_id: String by extra
val mc_version: String by extra
val registrate_version: String by extra
val create_version: String by extra
val flywheel_version: String by extra
val mod_version: String by extra
val jei_version: String by extra
val supplementaries_version: String by extra
val moonlight_lib_version: String by extra
val dye_depot_version: String by extra
val another_furniture_version: String by extra

plugins {
    id("com.possible-triangle.gradle") version("0.2.5")
}

withKotlin()

forge {
    dataGen(listOf("another_furniture", "supplementaries", "create"))

    includesMod("com.tterrag.registrate:Registrate:${registrate_version}")
}

// needed because of flywheel accessing the config too early
configure<MinecraftExtension> {
    runs {
        forEach {
            it.property("production", "true")
        }
    }
}

repositories {
    modrinthMaven()
    localMaven(project)

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
        }
    }
    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("com.jozufozu.flywheel")
            includeGroup("com.tterrag.registrate")
        }
    }
}

dependencies {
    modImplementation("com.simibubi.create:create-${mc_version}:${create_version}:slim") { isTransitive = false }
    modImplementation("com.jozufozu.flywheel:flywheel-forge-${mc_version}:${flywheel_version}")
    modImplementation("maven.modrinth:supplementaries:${supplementaries_version}")
    modImplementation("maven.modrinth:another-furniture:${another_furniture_version}")

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
        modRuntimeOnly("maven.modrinth:dye-depot:${dye_depot_version}")
        modRuntimeOnly("maven.modrinth:moonlight:${moonlight_lib_version}")
    }
}

enablePublishing {
    githubPackages()
}

uploadToCurseforge {
    dependencies {
        required("dye-depot")
    }
}

uploadToModrinth {
    dependencies {
        required("dye-depot")
    }

    syncBodyFromReadme()
}

enableSonarQube()