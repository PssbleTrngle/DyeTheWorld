plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("twigs")
}

dependencies {
    api(pack.modrinth.twigs)
}
