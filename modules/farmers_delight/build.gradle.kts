plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "farmersdelight"
}

dependencies {
    api(pack.modrinth.farmers.delight)
}
