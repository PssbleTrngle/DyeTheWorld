import com.possible_triangle.gradle.packwiz.ErrorStrategy

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.0.50")
    id("com.possible-triangle.packwiz") version ("1.0.50")
}

packwiz {
    verbose = true
    packs.named("default") {
        strategy = ErrorStrategy.FAIL
        from.set { rootDir.resolve("pack") }
    }
}
