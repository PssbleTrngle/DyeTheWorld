plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("windswept")
}

dependencies {
    api(libs.windswept) { isTransitive = false }
}
