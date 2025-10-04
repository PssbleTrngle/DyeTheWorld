
import com.possible_triangle.gradle.features.publishing.DependencyBuilder

val mod_id: String by extra
val mc_version: String by extra

plugins {
    alias(libs.plugins.gradle.helper)
    idea
}

withKotlin()

neoforge {
    enableMixins()

    dataGen {
        existing("dye_depot")
        existing("another_furniture")
        existing("supplementaries")
        existing("create")
        existing("comforts")
        existing("quark")
        existing("suppsquared")
        existing("farmersdelight")
        existing("domesticationinnovation")
        existing("createdeco")
        existing("railways")
        existing("chalk")
        existing("upgrade_aquatic")
        existing("waystones")
        existing("moreconcrete")
        existing("interiors")
    }

    mods.include(libs.registrate)
    // TODO do I need this?
    mods.include(libs.multikulti.core)
    // includesMod(libs.multikulti.registrate)
}

repositories {
    modrinthMaven()
    mavenLocal()

    nexus {
        content {
            includeGroup("com.possible-triangle")
            includeGroup("com.ninni.dye_depot")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
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
}

dependencies {
    modImplementation(libs.multikulti.datagen)
    modImplementation(variantOf(libs.create) {
        classifier("slim")
    }) {
        isTransitive = false
    }
    modImplementation(libs.ponder)
    modCompileOnly(libs.flywheel.api)
    modImplementation(pack.modrinth.another.furniture)
    modImplementation(pack.modrinth.comforts)
    modImplementation(pack.modrinth.moonlight)
    modImplementation(pack.modrinth.supplementaries)
    modImplementation(pack.modrinth.supplementaries.squared)
    modImplementation(pack.modrinth.quark)
    modImplementation(pack.modrinth.zeta)
    modImplementation(pack.modrinth.farmers.delight)
    modImplementation(pack.modrinth.clayworks)
    modImplementation(pack.modrinth.upgrade.aquatic)
    modImplementation(pack.modrinth.blueprint)
    modImplementation(pack.modrinth.chalk.mod)
    modImplementation(pack.modrinth.create.deco)
    modImplementation(pack.modrinth.domestication.innovation)
    modImplementation(pack.modrinth.alexs.caves)
    modImplementation(pack.modrinth.alexs.mobs)
    modImplementation(pack.modrinth.waystones)
    modImplementation(pack.modrinth.create.steam.n.rails)
    modImplementation(libs.dye.depot)

    modRuntimeOnly(libs.flywheel)
    modRuntimeOnly(libs.jei)
    modRuntimeOnly(pack.modrinth.jade)
    modRuntimeOnly(pack.modrinth.citadel)
    modRuntimeOnly(pack.modrinth.interiors)
    modRuntimeOnly(pack.modrinth.curios)
    modRuntimeOnly(pack.modrinth.ars.nouveau)
    modRuntimeOnly(pack.modrinth.gallery)
    modRuntimeOnly(pack.modrinth.more.concrete)
    modRuntimeOnly(pack.modrinth.balm)
    // modRuntimeOnly(pack.modrinth.polytone)
    modRuntimeOnly(pack.modrinth.amendments)
    modRuntimeOnly(pack.modrinth.registry.dump)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

enablePublishing {
    githubPackages()
    nexus()
}

fun DependencyBuilder.addDependencies() {
    required("dye-depot")
    optional("create")
    optional("another-furniture")
    optional("comforts")
    optional("clayworks")
    optional("farmers-delight")
    optional("quark")
    optional("domestication-innovation")
    optional("supplementaries")
    optional("supplementaries-squared")
    optional("alexs-caves")
    optional("ars-nouveau")
    optional("create-deco")
    optional("create-steam-n-rails")
    optional("upgrade-aquatic")
    optional("more-concrete")
    optional("waystones")
    optional("interiors")
}

uploadToCurseforge {
    dependencies {
        addDependencies()
        optional("chalk")
    }
}

uploadToModrinth {
    dependencies {
        addDependencies()
        optional("chalk-mod")
    }

    syncBodyFromReadme()
}

idea {
    module {
        excludeDirs.add(file("polytone"))
    }
}

enableSonarQube()
enableSpotless()