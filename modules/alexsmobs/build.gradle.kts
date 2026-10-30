plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("alexs_mobs") {
        id = "alexsmobs"
    }
}

dependencies {
    api(pack.modrinth.citadel)
    api(pack.modrinth.alexs.mobs)
}
