plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    runtimeOnly(pack.modrinth.gallery)
    api(pack.modrinth.blueprint)
    api(pack.modrinth.clayworks)
}
