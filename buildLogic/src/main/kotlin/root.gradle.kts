plugins {
    idea
    id("com.possible-triangle.core")
}

idea {
    module {
        excludeDirs.add(file("polytone"))
        excludeDirs.add(file("scripts"))
    }
}

enableSpotless()
