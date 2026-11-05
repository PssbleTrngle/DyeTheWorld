plugins {
    id("com.possible-triangle.neoforge")
    id("module")
}

compat.default {
    id = "moreconcrete"
}

dependencies {
    api(pack.modrinth.more.concrete)
}
