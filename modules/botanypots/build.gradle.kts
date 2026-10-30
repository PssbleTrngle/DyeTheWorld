plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("botany_pots") {
        id = "botanypots"
        dataOnly()
    }
}

dependencies {
    runtimeOnly(pack.modrinth.botany.pots)
}
