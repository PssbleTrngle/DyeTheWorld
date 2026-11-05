plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "interiors"
    dataOnly()
}

neoforge.dataGen {
    existing("create")
    existing(project(":create"))
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.interiors)
}
