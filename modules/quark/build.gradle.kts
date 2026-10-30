plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("quark")
}

dependencies {
    api(pack.modrinth.quark)
    api(pack.modrinth.zeta)
}
