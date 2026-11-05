plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default()

dependencies {
    api(libs.windswept) { isTransitive = false }
}
