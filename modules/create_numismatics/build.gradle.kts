plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    dataOnly()
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.numismatics)
}
