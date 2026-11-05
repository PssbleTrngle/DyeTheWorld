plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    noCode()
}

dependencies {
    runtimeOnly(pack.modrinth.geckolib)
    runtimeOnly(pack.modrinth.curios)
    runtimeOnly(pack.modrinth.ars.nouveau)
}
