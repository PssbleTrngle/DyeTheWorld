plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "alexscaves"
}

dependencies {
    api(pack.modrinth.citadel)
    api(pack.modrinth.alexs.caves.unofficial.port)
}
