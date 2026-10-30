plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("create_interiors") {
        id = "interiors"
        dataOnly()
    }
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.interiors)
}
