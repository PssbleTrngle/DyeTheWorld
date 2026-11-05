plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.spelunkery)
}
