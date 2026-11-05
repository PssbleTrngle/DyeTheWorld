import com.possible_triangle.gradle.modModules

plugins {
    id("com.possible-triangle.neoforge")
    id("conventions")
}

modModules.forEach { module ->
    evaluationDependsOn(module.path)

    dependencies {
        implementation(project(module.path))
        jarJar(project(module.path))
    }
}

dependencies {
    runtimeOnly(libs.jei)
    runtimeOnly(pack.modrinth.jade)
}

/*
dependencies {
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
    modRuntimeOnly(pack.modrinth.upgrade.aquatic)
    modImplementation(pack.modrinth.blueprint)
    modImplementation(pack.modrinth.chalk.mod)
    modImplementation(pack.modrinth.create.deco)
    modRuntimeOnly(libs.sable) { isTransitive = false }
    modImplementation(libs.create.simulated) { isTransitive = false }
    modImplementation(libs.create.aeronautics) { isTransitive = false }
    modImplementation(pack.modrinth.redomesticate)
    modImplementation(pack.modrinth.alexs.caves.unofficial.port)
    modCompileOnly(pack.modrinth.alexs.mobs)
    modImplementation(pack.modrinth.waystones)
    modCompileOnly(
        pack.modrinth.create.steam.n.rails
            .get1(),
    )
    modImplementation(pack.modrinth.numismatics)
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
    modRuntimeOnly(pack.modrinth.citadel)
    modImplementation(pack.modrinth.interiors)
    modImplementation(pack.modrinth.create.bits.n.bobs)
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
    modRuntimeOnly(pack.modrinth.azimuth.api)
    modRuntimeOnly(pack.modrinth.strut.your.stuff)
    modImplementation(pack.modrinth.naturalist)
    modImplementation(pack.modrinth.labels)

    modRuntimeOnly(pack.modrinth.jadens.nether.expansion)
    modRuntimeOnly(pack.modrinth.elysium.api)
    modRuntimeOnly(pack.modrinth.lodestonelib)
}
*/

upload {
    curseforge {
        dependencies {
            optional("chalk")
            optional("openblocks-elevator")
            optional("windsweptmod")
            optional("alexs-caves-unofficial-port")
        }
    }

    modrinth {
        dependencies {
            optional("chalk-mod")
            optional("windswept")
            optional("alexs-caves-(unofficial-port)")
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
            optional("ars-nouveau")
            optional("create-deco")
            optional("create-steam-n-rails")
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
            optional("create-bits-n-bobs")
            optional("labels")
            optional("jadens-nether-expansion")
        }
    }
}
