plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("simulated")
    create("aeronautics")

    configureEach {
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

    runtimeOnly(libs.sable) { isTransitive = false }
    api(libs.create.simulated) { isTransitive = false }
    api(libs.create.aeronautics) { isTransitive = false }
}
