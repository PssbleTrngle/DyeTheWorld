plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    dataOnly()
}

dependencies {
    api(project(":create"))
    compileOnlyApi(
        pack.modrinth.create.steam.n.rails
            .get1(),
    )
}
