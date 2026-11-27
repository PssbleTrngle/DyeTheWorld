plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    api(pack.modrinth.quark)
    api(pack.modrinth.zeta)
}
