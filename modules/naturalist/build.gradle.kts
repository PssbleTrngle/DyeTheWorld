plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("naturalist") {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.naturalist)
}
