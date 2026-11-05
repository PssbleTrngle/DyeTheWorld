plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "interiors"
    dataOnly()
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.interiors)
}
