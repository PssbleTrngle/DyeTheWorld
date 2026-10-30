plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("numismatics") {
        dataOnly()
    }
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.numismatics)
}
