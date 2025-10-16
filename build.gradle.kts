val mod_id: String by extra
val mc_version: String by extra

plugins {
    id("com.possible-triangle.neoforge")
    idea
}

withKotlin()

mod {
    mods.include(libs.registrate)
}

neoforge {
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
        existing("snowyspirit")
        existing("connectedglass")
        existing("botanypots")
    }
}

repositories {
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
    modApi(libs.multikulti.core)
    modApi(libs.multikulti.datagen)

    modApi(variantOf(libs.create) {
        classifier("slim")
    }) {
        isTransitive = false
    }
    modApi(libs.ponder)
    modCompileOnly(libs.flywheel.api)
    modApi(pack.modrinth.another.furniture)
    modApi(pack.modrinth.comforts)
    modApi(pack.modrinth.moonlight)
    modApi(pack.modrinth.supplementaries)
    modApi(pack.modrinth.supplementaries.squared)
    modCompileOnly(pack.modrinth.quark)
    modCompileOnly(pack.modrinth.zeta)
    modApi(pack.modrinth.farmers.delight)
    modApi(pack.modrinth.clayworks)
    modApi(pack.modrinth.upgrade.aquatic)
    modApi(pack.modrinth.blueprint)
    modApi(pack.modrinth.chalk.mod)
    modCompileOnly(pack.modrinth.create.deco)
    modCompileOnly(pack.modrinth.domestication.innovation)
    modCompileOnly(pack.modrinth.alexs.caves)
    modCompileOnly(pack.modrinth.alexs.mobs)
    modApi(pack.modrinth.waystones)
    modCompileOnly(pack.modrinth.create.steam.n.rails)
    modApi(libs.dye.depot)
    //modApi(pack.modrinth.snowy.spirit)
    modApi(libs.snowy.spirit)
    modApi(pack.modrinth.fusion.connected.textures)

    //modRuntimeOnly(pack.modrinth.immersiveengineering)
    modRuntimeOnly(libs.flywheel)
    modRuntimeOnly(libs.jei)
    modRuntimeOnly(pack.modrinth.jade)
    // modRuntimeOnly(pack.modrinth.citadel)
    // modRuntimeOnly(pack.modrinth.interiors)
    modRuntimeOnly(pack.modrinth.curios)
    modRuntimeOnly(pack.modrinth.geckolib)
    modRuntimeOnly(pack.modrinth.ars.nouveau)
    modRuntimeOnly(pack.modrinth.gallery)
    modRuntimeOnly(pack.modrinth.more.concrete)
    modRuntimeOnly(pack.modrinth.balm)
    // modRuntimeOnly(pack.modrinth.polytone)
    modRuntimeOnly(pack.modrinth.amendments)
    modRuntimeOnly(pack.modrinth.registry.dump)
    modRuntimeOnly(pack.curseforge.openblocks.elevator)
    modRuntimeOnly(pack.modrinth.connected.glass)
    modRuntimeOnly(pack.modrinth.supermartijn642s.core.lib)
    modRuntimeOnly(pack.modrinth.prickle)
    modRuntimeOnly(pack.modrinth.bookshelf.lib)
    modRuntimeOnly(pack.modrinth.botany.pots)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

upload {
    maven {
        githubPackages()
        nexus()
    }

    curseforge {
        dependencies {
            optional("chalk")
            optional("openblocks-elevator")
        }
    }

    modrinth {
        dependencies {
            optional("chalk-mod")
        }

        syncBodyFromReadme()
    }

    forEach {
        dependencies {
            required("dye-depot")
            optional("create")
            optional("another-furniture")
            optional("comforts")
            optional("clayworks")
            optional("farmers-delight")
            // optional("quark")
            // optional("domestication-innovation")
            optional("supplementaries")
            optional("supplementaries-squared")
            // optional("alexs-caves")
            optional("ars-nouveau")
            // optional("create-deco")
            // optional("create-steam-n-rails")
            optional("upgrade-aquatic")
            optional("more-concrete")
            optional("waystones")
            // optional("interiors")
            optional("snowy-spirit")
            optional("botany-pots")
            optional("connected-glass")
        }
    }
}

idea {
    module {
        excludeDirs.add(file("polytone"))
    }
}

enableSonarQube()
enableSpotless()