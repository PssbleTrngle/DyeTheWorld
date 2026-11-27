plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    noCode()
}

dependencies {
    api(project(":create"))
    api(pack.modrinth.create.dyeable.chains)
}
