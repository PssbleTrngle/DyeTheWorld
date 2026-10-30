plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("create_deco") {
        id = "createdeco"
        dataOnly()
    }
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.create.deco)
}
