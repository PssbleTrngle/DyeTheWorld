plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("another_furniture")
}

dependencies {
    api(pack.modrinth.another.furniture)
}
