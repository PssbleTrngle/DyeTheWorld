plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    dataOnly()
}

dependencies {
    api(pack.modrinth.naturalist)
}
