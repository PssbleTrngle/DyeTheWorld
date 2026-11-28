import com.possible_triangle.gradle.modifyReleaseMetadata

plugins {
    id("com.possible-triangle.neoforge")
    id("conventions")
    id("templates")
}

neoForge.runs.removeAll { true }

dependencies {
    compileOnly(project(":core"))
    compileOnly(pack.modrinth.interiors)
}

modifyReleaseMetadata {
    exclude = true
}
