plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "vanillabackport"
}

dependencies {
    api(pack.modrinth.platform)
    api(pack.modrinth.vanillabackport)
}
