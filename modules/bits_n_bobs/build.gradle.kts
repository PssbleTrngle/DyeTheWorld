plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    dataOnly()
}

dependencies {
    api(project(":create"))
    runtimeOnly(pack.modrinth.strut.your.stuff)
    runtimeOnly(pack.modrinth.azimuth.api)
    api(pack.modrinth.create.bits.n.bobs)
}
