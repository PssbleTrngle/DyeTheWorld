plugins {
    id("com.possible-triangle.forge")
    idea
}

withKotlin()

forge {
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
        existing("snowyspirit")
        existing("connectedglass")
        existing("botanypots")
        existing("simulated")
        existing("aeronautics")
        existing("spelunkery")
        existing("twigs")
        existing("sleep_tight")
        existing("windswept")
    }
}

repositories {
    nexus {
        content {
            includeGroup("com.possible-triangle")
            includeGroup("com.ninni.dye_depot")
            includeGroup("net.mehvahdjukaar")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
        }
    }
    maven {
        url = uri("https://maven.tterrag.com/")
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
}

dependencies {
    modInclude(libs.registrate)
    modInclude(libs.multikulti.datagen.fix)

    modImplementation(libs.multikulti.core)
    modImplementation(libs.multikulti.datagen)

    modImplementation(
        variantOf(libs.create) {
            classifier("slim")
        },
    ) {
        isTransitive = false
    }
    modImplementation(libs.ponder)
    modCompileOnly(libs.flywheel.api)
    modImplementation(pack.modrinth.another.furniture)
    modImplementation(pack.modrinth.comforts)
    modImplementation(variantOf(libs.moonlight) { classifier("forge") })
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
    modImplementation(pack.modrinth.numismatics)
    modImplementation(libs.dye.depot)
    modImplementation(pack.modrinth.snowy.spirit)
    modImplementation(pack.modrinth.fusion.connected.textures)
    modImplementation(pack.modrinth.vanillabackport)
    modImplementation(pack.modrinth.spelunkery)
    modImplementation(pack.modrinth.twigs)
    modImplementation(pack.modrinth.sleep.tight)
    modImplementation(pack.modrinth.windswept)
    // modRuntimeOnly(pack.modrinth.immersiveengineering)
    modRuntimeOnly(libs.flywheel)
    modRuntimeOnly(libs.jei)
    modRuntimeOnly(pack.modrinth.jade)
    modRuntimeOnly(pack.modrinth.citadel)
    // modRuntimeOnly(pack.modrinth.interiors)
    modRuntimeOnly(pack.modrinth.curios)
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
    modRuntimeOnly(pack.modrinth.bookshelf.lib)
    modRuntimeOnly(pack.modrinth.botany.pots)
    modRuntimeOnly(pack.modrinth.platform)
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
            optional("windsweptmod")
        }
    }

    modrinth {
        dependencies {
            optional("chalk-mod")
            optional("windswept")
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
            optional("snowy-spirit")
            optional("botany-pots")
            optional("connected-glass")
            optional("vanillabackport")
            optional("create-aeronautics")
            optional("spelunkery")
            optional("twigs")
            optional("numismatics")
            optional("sleep-tight")
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
