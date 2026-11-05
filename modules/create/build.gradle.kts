plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    versionRange = "[6.0.0,6.1.0)"
    dataOnly()
}

dependencies {
    api(libs.ponder) {
        isTransitive = false
    }
    api(libs.flywheel)
    compileOnlyApi(libs.flywheel.api)
    api(
        variantOf(libs.create) {
            classifier("slim")
        },
    ) {
        isTransitive = false
    }
}
