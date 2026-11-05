plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "createdeco"
    dataOnly()
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.create.deco)
}
