plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("chalk") {
        dataOnly()
    }
}

dependencies {
    api(pack.modrinth.chalk.mod)
}
