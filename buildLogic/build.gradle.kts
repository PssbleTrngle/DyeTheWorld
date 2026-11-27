plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenLocal()
    maven("https://registry.somethingcatchy.net/repository/maven-public/")
}

dependencies {
    implementation(libs.blossom)
    implementation("com.possible-triangle:neoforge:1.4.+")
}
