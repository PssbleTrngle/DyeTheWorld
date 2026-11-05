plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "alexsmobs"
    noCode()
}

dependencies {
    api(pack.modrinth.citadel)
    api(pack.modrinth.alexs.mobs)
}
