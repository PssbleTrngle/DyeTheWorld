package com.possible_triangle.gradle

import com.possible_triangle.gradle.CompatMod
import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.internal.extensions.stdlib.capitalized
import java.io.Serializable

internal data class CompatModProperties(
    val id: String,
    val namespace: String,
    val name: String,
    val file: String,
    val registrate: String?,
    val versionRange: String,
    val type: String,
) : Serializable

enum class CompatType {
    FULL,
    DATA_ONLY,
    RESOURCES_ONLY,
}

// TODO migrate?

interface CompatMod : Named {
    val versionRange: Property<String>
    val type: Property<CompatType>
    val id: Property<String>
    val namespace: Property<String>
    val registrate: Property<Boolean>

    fun dataOnly() = type.set(CompatType.DATA_ONLY)
    fun noCode() = type.set(CompatType.RESOURCES_ONLY)
}

abstract class CompatModsExtension(private val project: Project) {
    abstract val mods: NamedDomainObjectContainer<CompatMod>
    fun mods(action: Action<in NamedDomainObjectContainer<CompatMod>>) = action.execute(mods)

    fun default(action: Action<in CompatMod> = Action {}) = mods.create(project.name, action)
}

fun String.camelCase() = split("_").joinToString("") { it.capitalized() }

internal fun CompatModsExtension.modProperties() = mods.map { properties ->
    val id = properties.id.getOrElse(properties.name)
    CompatModProperties(
        id = id,
        namespace = properties.namespace.getOrElse(id),
        name = properties.name,
        file = properties.name.camelCase(),
        registrate = ("REGISTRATE" + if (mods.count { it.registrate.getOrElse(true) } == 1) "" else "_${properties.name.uppercase()}")
            .takeIf { properties.registrate.getOrElse(true) },
        versionRange = properties.versionRange.getOrElse("*"),
        type = properties.type.getOrElse(CompatType.FULL).name.lowercase()
    )
}
