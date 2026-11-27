plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "snowyspirit"
    dataOnly()
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.snowy.spirit)
}
