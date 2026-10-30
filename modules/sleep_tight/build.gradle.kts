plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("sleep_tight") {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.moonlight)
    api(pack.modrinth.sleep.tight)
}
