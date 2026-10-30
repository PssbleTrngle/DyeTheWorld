package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey

class TagHelper<T : Any>(private val registry: ResourceKey<Registry<T>>) {

    fun create(namespace: String, path: String) = TagKey.create(registry, namespace.createId(path))

    fun loader(path: String) = create("c", path)

}

object DyedTags {
    @JvmStatic
    val ITEMS = TagHelper(Registries.ITEM)

    @JvmStatic
    val BLOCKS = TagHelper(Registries.BLOCK)
}
