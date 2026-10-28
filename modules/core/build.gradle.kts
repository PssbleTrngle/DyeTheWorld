plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

dependencies {
    modInclude(libs.registrate)

    modApi(libs.dye.depot)
    // TODO re-check
    // accessTransformers(libs.dye.depot)
}
