plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    runtimeOnly(pack.modrinth.jinxedlib)
    api(pack.modrinth.artsandcrafts)

    implementation(project(":bits_n_bobs"))
    implementation(project(":botany_pots"))
    implementation(project(":clayworks"))
    implementation(project(":comforts"))
    implementation(project(":create"))
    implementation(project(":create_deco"))
    implementation(project(":create_interiors"))
    implementation(project(":aeronautics"))
    implementation(project(":alexs_caves"))
    implementation(project(":more_concrete"))
    implementation(project(":connected_glass"))
    implementation(project(":farmers_delight"))
}
