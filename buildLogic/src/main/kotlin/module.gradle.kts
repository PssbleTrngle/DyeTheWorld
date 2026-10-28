import com.possible_triangle.gradle.modifyReleaseMetadata
import com.possible_triangle.gradle.neoforge.NeoforgeExtension
import org.gradle.internal.extensions.stdlib.capitalized
import net.kyori.blossom.BlossomExtension
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

sourceSets.main {
    configure<BlossomExtension> {
        javaSources {
            templates(rootProject.file("templates/java"))
        }

        templateSets.configureEach {
            property("module", project.name)
            property("moduleName", project.name.capitalized())
            property("slug", rootProject.mod.id.map { "$it-${project.name}" })
        }
    }
}
