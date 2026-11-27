plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    versionRange = "[2.1.0,)"
}

dependencies {
    api(pack.modrinth.redomesticate)
}
