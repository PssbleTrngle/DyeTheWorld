import gradle.kotlin.dsl.accessors._660882a4726682c08a462e6eb8367760.main
import gradle.kotlin.dsl.accessors._660882a4726682c08a462e6eb8367760.mod
import gradle.kotlin.dsl.accessors._660882a4726682c08a462e6eb8367760.sourceSets
import net.kyori.blossom.BlossomExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke

plugins {
    id("com.possible-triangle.core")
    id("net.kyori.blossom")
}

sourceSets.main {
    configure<BlossomExtension> {
        resources {
            templates(rootProject.file("templates/resources"))
        }

        templateSets.configureEach {
            property("baseModId", rootProject.mod.id)
            property("baseModName", rootProject.mod.name)
            property("modId", mod.id)
            property("modName", mod.name)
            properties.put("compatMods", emptyList<String>())
            properties.put("mixinConfigs", provider {
                listOfNotNull(
                    "${rootProject.mod.id.get()}.${project.name}.mixins.json".takeIf {
                        file("src/main/resources/${it}").exists()
                    }
                )
            })
        }
    }
}
