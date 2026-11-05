import com.possible_triangle.gradle.CompatModsExtension
import com.possible_triangle.gradle.modProperties
import com.possible_triangle.gradle.modifyReleaseMetadata
import com.possible_triangle.gradle.neoforge.NeoforgeExtension
import com.possible_triangle.gradle.camelCase
import net.kyori.blossom.BlossomExtension
import net.neoforged.moddevgradle.dsl.NeoForgeExtension
import org.gradle.kotlin.dsl.configure

plugins {
    id("conventions")
}

modifyReleaseMetadata {
    exclude = true
}

configure<NeoforgeExtension> {
    dataGen {
        owner = project
    }
}

val compat = extensions.create<CompatModsExtension>("compat")

if (project.name != "core") {
    dependencies {
        add("api", project(":core"))
        add("runtimeOnly", project(":data"))
    }
}

sourceSets.main {
    resources.srcDir(file("src/dyed/resources"))

    configure<BlossomExtension> {
        javaSources {
            templates(rootProject.file("templates/java"))
        }

        kotlinSources {
            templates(rootProject.file("templates/kotlin"))
        }

        templateSets.configureEach {
            property("module", project.name)
            property("moduleName", project.name.camelCase())
            properties.put("compatMods", provider {
                compat.modProperties()
            })
        }
    }
}

configure<NeoForgeExtension> {
    runs.named("data") {
        programArguments.addAll(provider {
            val existing = compat.mods.map { it.id.getOrElse(it.name) } + "dye_depot"
            existing.flatMap { listOf("--existing-mod", it) }
        })
    }
}
