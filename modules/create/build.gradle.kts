plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("create") {
        versionRange = "[6.0.0,6.1.0)"
        dataOnly()
    }
}

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
