plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    dataOnly()
}

dependencies {
    runtimeOnly(pack.modrinth.balm)
    api(pack.modrinth.waystones)
}
