plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("vanilla_backport") {
        id = "vanillabackport"
    }
}

dependencies {
    api(pack.modrinth.platform)
    api(pack.modrinth.vanillabackport)
}
