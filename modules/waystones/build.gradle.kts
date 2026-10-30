plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("waystones") {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.waystones)
}
