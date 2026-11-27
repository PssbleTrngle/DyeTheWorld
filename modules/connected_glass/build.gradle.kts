plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "connectedglass"
    dataOnly()
}

dependencies {
    api(pack.modrinth.fusion.connected.textures)
}
