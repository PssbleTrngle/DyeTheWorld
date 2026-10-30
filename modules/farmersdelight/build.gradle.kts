plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("farmers_delight") {
        id = "farmersdelight"
    }
}

dependencies {
    api(pack.modrinth.farmers.delight)
}
