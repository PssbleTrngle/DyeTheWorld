plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

dependencies {
    apiInclude(libs.registrate)

    api(libs.dye.depot)
    api(pack.modrinth.moonlight)

    runtimeOnly(libs.jei)
    runtimeOnly(pack.modrinth.jade)
}
