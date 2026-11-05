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

tasks.processResources {
    filesMatching("META-INF/neoforge.mods.toml") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

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
