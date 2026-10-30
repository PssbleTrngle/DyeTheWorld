package com.possible_triangle.gradle

import com.possible_triangle.gradle.neoforge.NeoforgeExtension
import net.kyori.blossom.BlossomExtension
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.Serializable

val Project.modModules
    get() =
        rootProject.subprojects
            .filter { it.projectDir.relativeTo(it.rootDir).startsWith("modules/") }

private data class CompatMod(
    val id: String,
    val registrate: String,
    val versionRange: String,
) : Serializable

fun Project.setupModule(vararg compatMods: String) {
    dependencies {
        add("api", project(":core"))
        add("runtimeOnly", project(":data"))
    }

    configure<NeoforgeExtension> {
        dataGen {
            compatMods.forEach {
                existing(it)
            }
        }
    }

    configure<SourceSetContainer> {
        named("main") {
            configure<BlossomExtension> {
                templateSets.configureEach {
                    properties.put("compatMods", compatMods.map {
                        CompatMod(
                            id = it,
                            registrate = "REGISTRATE" + if (compatMods.size == 1) "" else "_${it.capitalized()}",
                            versionRange = "*",
                        )
                    })
                    properties.put("moduleDependencies", listOf("core"))
                }
            }
        }
    }
}
