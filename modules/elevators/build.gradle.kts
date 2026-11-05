plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "elevatorid"
    dataOnly()
}

dependencies {
    api(pack.modrinth.jadens.nether.expansion)
    api(pack.modrinth.elysium.api)
    api(pack.modrinth.lodestonelib)
}
