plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("connected_glass") {
        id = "connectedglass"
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.fusion.connected.textures)
}
