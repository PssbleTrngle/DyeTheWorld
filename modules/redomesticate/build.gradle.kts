plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("redomesticate")
}

dependencies {
    api(pack.modrinth.redomesticate)
}
