import com.possible_triangle.gradle.setupModule

plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

setupModule("create")

dependencies {
    api(libs.ponder)
    compileOnlyApi(libs.flywheel.api)
    api(
        variantOf(libs.create) {
            classifier("slim")
        },
    ) {
        isTransitive = false
    }
}
