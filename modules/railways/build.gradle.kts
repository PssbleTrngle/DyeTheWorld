plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("railways") {
        dataOnly()
    }
}

dependencies {
    api(project(":create"))
    compileOnlyApi(
        pack.modrinth.create.steam.n.rails
            .get1(),
    )
}
