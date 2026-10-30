plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("spelunkery")
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.spelunkery)
}
