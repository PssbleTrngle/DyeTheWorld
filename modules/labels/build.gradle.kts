plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("labels")
}

dependencies {
    api(pack.modrinth.labels)
}
