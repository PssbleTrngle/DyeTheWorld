plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

dependencies {
    apiInclude(libs.registrate)
    apiInclude(libs.multikulti.core)
    apiInclude(libs.multikulti.registrate)

    api(libs.dye.depot)
    api(pack.modrinth.moonlight)

    runtimeOnly(libs.jei)
    runtimeOnly(pack.modrinth.jade)
}
