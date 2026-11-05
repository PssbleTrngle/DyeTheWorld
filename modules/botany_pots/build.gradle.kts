plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "botanypots"
    dataOnly()
}

dependencies {
    runtimeOnly(pack.modrinth.prickle)
    runtimeOnly(pack.modrinth.bookshelf.lib)
    runtimeOnly(pack.modrinth.botany.pots)
}
