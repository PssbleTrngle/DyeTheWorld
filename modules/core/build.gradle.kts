plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

dependencies {
    apiInclude(libs.registrate)

    api(libs.dye.depot)
    api(pack.modrinth.moonlight)
    // TODO re-check
    // accessTransformers(libs.dye.depot)
}
