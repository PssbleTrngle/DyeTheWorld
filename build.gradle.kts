import com.possible_triangle.gradle.modModules
import org.gradle.internal.extensions.stdlib.capitalized

plugins {
    id("root")
    id("com.possible-triangle.neoforge") apply false
}

withKotlin()

modModules.forEach {
    it.extra.set("mod_id", "${mod.id.get()}_${it.name}")
    it.extra.set("mod_name", "${mod.name.get()} ${it.name.capitalized()}")

    it.dependencies {
        implementation(libs.multikulti.datagen)
    }
}

val runModuleData =
    tasks.register("runModuleData") {
        description = "run data generators for all modules"
        modModules.forEach {
            dependsOn(it.tasks.getByName("runData"))
        }
    }
