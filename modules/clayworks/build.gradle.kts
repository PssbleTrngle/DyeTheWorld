plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("clayworks")
}

dependencies {
    api(pack.modrinth.blueprint)
    api(pack.modrinth.clayworks)
}
