plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat {
    default {
        dataOnly()
    }
    mods.create("jinxed_lib") {
        id = "jinxedlib"
        registrate = false
    }
}

dependencies {
    api(pack.modrinth.jinxedlib)
    api(pack.modrinth.artsandcrafts)

    implementation(project(":clayworks"))
}
