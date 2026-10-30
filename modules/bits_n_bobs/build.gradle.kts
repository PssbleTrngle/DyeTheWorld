plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.mods {
    create("bits_n_bobs") {
        dataOnly()
    }
}

dependencies {
    // TODO reuse somehow?
    // or just depend on create module?
    api(libs.ponder)
    compileOnlyApi(libs.flywheel.api)
    api(
        variantOf(libs.create) {
            classifier("slim")
        },
    ) {
        isTransitive = false
    }

    api(pack.modrinth.create.bits.n.bobs)
}
