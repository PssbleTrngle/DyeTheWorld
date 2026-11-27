plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("supplementaries")
    create("supplementaries_squared") {
        id = "suppsquared"
    }
    create("amendments")

    configureEach {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.supplementaries)
    api(pack.modrinth.supplementaries.squared)
    api(pack.modrinth.amendments)
}
