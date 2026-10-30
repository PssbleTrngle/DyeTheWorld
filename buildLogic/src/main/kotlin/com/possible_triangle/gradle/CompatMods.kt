package com.possible_triangle.gradle

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.Property
import org.gradle.internal.extensions.stdlib.capitalized
import java.io.Serializable

internal data class CompatModProperties(
    val id: String,
    val name: String,
    val file: String,
    val registrate: String,
    val versionRange: String,
    val dataOnly: Boolean,
) : Serializable

interface CompatMod : Named {
    val versionRange: Property<String>
    val dataOnly: Property<Boolean>
    val id: Property<String>

    fun dataOnly() = dataOnly.set(true)
}

interface CompatModsExtension {
    val mods: NamedDomainObjectContainer<CompatMod>
    fun mods(action: Action<in NamedDomainObjectContainer<CompatMod>>) = action.execute(mods)
}

fun String.camelCase() = split("_").joinToString("") { it.capitalized() }

internal fun CompatModsExtension.modProperties() = mods.map {
    CompatModProperties(
        id = it.id.getOrElse(it.name),
        name = it.name,
        file = it.name.camelCase(),
        registrate = "REGISTRATE" + if (mods.size == 1) "" else "_${it.name.uppercase()}",
        versionRange = it.versionRange.getOrElse("*"),
        dataOnly = it.dataOnly.getOrElse(false)
    )
}
