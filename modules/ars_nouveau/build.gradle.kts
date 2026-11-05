plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    noCode()
}

dependencies {
    runtimeOnly(pack.modrinth.ars.nouveau)
}
