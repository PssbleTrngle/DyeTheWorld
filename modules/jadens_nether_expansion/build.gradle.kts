plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("jadens_nether_extension") {
        id = "netherexp"
    }
}

dependencies {
    api(pack.modrinth.jadens.nether.expansion)
    api(pack.modrinth.elysium.api)
    api(pack.modrinth.lodestonelib)
}
