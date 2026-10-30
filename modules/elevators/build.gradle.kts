plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("elevators") {
        id = "elevatorid"
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.jadens.nether.expansion)
    api(pack.modrinth.elysium.api)
    api(pack.modrinth.lodestonelib)
}
