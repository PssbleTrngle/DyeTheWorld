plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("more_concrete") {
        id = "moreconcrete"
    }
}

dependencies {
    api(pack.modrinth.more.concrete)
}
