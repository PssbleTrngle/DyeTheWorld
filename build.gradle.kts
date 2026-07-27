plugins {
    id("com.possible-triangle.neoforge")
    idea
}

withKotlin()

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
            includeGroup("com.rosemods")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
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

dependencies {
    modInclude(libs.registrate)

    modApi(libs.multikulti.core)
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
    // modRuntimeOnly(libs.sable) { isTransitive = false }
    modCompileOnly(libs.create.simulated) { isTransitive = false }
    modCompileOnly(libs.create.aeronautics) { isTransitive = false }
    modImplementation(pack.modrinth.redomesticate)
    modCompileOnly(pack.modrinth.alexs.caves)
    modCompileOnly(pack.modrinth.alexs.mobs)
    modImplementation(pack.modrinth.waystones)
    modImplementation(
        pack.modrinth.create.steam.n.rails
            .get1(),
    )
    modImplementation(pack.modrinth.numismatics)
    modImplementation(libs.dye.depot)
    accessTransformers(libs.dye.depot)
    modImplementation(pack.modrinth.snowy.spirit)
    modImplementation(pack.modrinth.fusion.connected.textures)
    modImplementation(pack.modrinth.vanillabackport)
    modImplementation(pack.modrinth.spelunkery)
    modImplementation(pack.modrinth.twigs)
    modImplementation(pack.modrinth.sleep.tight)
    modImplementation(libs.windswept) { isTransitive = false }
    // modRuntimeOnly(pack.modrinth.immersiveengineering)
    modRuntimeOnly(libs.flywheel)
    modRuntimeOnly(libs.jei)
    modRuntimeOnly(pack.modrinth.jade)
    // modRuntimeOnly(pack.modrinth.citadel)
    modImplementation(pack.modrinth.interiors)
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
            optional("redomesticate")
            optional("supplementaries")
            optional("supplementaries-squared")
            // optional("alexs-caves")
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
