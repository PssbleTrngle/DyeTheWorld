plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    noCode()
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.vista.tv)
}
