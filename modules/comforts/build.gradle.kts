plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("comforts") {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.comforts)
}
