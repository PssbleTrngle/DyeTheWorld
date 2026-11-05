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

neoforge.dataGen {
    existing("create")
    existing(project(":create"))
}

dependencies {
    api(project(":create"))
    runtimeOnly(libs.sable) { isTransitive = false }
    api(libs.create.simulated) { isTransitive = false }
    api(libs.create.aeronautics) { isTransitive = false }
}
