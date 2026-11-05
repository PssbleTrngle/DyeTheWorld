plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    api(pack.modrinth.blueprint)
    api(pack.modrinth.gallery)
    api(libs.windswept) { isTransitive = false }
}
