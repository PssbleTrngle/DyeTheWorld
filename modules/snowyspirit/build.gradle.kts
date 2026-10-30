plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("snowy_spirit") {
        id = "snowyspirit"
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.snowy.spirit)
}
