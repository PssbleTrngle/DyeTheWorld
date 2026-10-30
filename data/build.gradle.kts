import com.possible_triangle.gradle.modifyReleaseMetadata

plugins {
    id("com.possible-triangle.neoforge")
    id("conventions")
}

neoForge.runs.removeAll { true }

dependencies {
    compileOnly(project(":core"))
    implementation(pack.modrinth.interiors)
}

modifyReleaseMetadata {
    exclude = true
}
