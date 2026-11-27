package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

class TagHelper<T : Any>(
    private val registry: ResourceKey<Registry<T>>,
) {
    fun create(
        namespace: String,
        path: String,
    ): TagKey<T> = TagKey.create(registry, namespace.createId(path))

    fun vanilla(path: String) = create(ResourceLocation.DEFAULT_NAMESPACE, path)

    fun loader(path: String) = create("c", path)

    fun copy(from: TagKey<*>) = create(from.location().namespace, from.location().path)

    fun material(name: String) =
        Material(
            ingot = loader("ingots/$name"),
            nugget = loader("nuggets/$name"),
            plate = loader("plates/$name"),
        )
}

object DyedTags {
    @JvmStatic
    val ITEMS = TagHelper(Registries.ITEM)

    @JvmStatic
    val BLOCKS = TagHelper(Registries.BLOCK)

    object Materials {
        val IRON = ITEMS.material("iron")
        val BRASS = ITEMS.material("brass")
    }
}

data class Material<T>(
    val ingot: TagKey<T>,
    val nugget: TagKey<T>,
    val plate: TagKey<T>,
)
