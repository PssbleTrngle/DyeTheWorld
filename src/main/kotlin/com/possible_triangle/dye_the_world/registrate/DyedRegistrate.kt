package com.possible_triangle.dye_the_world.registrate

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateTagsProvider
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

class DyedRegistrate private constructor(
    modid: String,
) : AbstractRegistrate<DyedRegistrate>(modid) {
    companion object {
        private val REGISTRATES = hashMapOf<String, DyedRegistrate>()

        fun create(modid: String) = REGISTRATES.getOrPut(modid) { DyedRegistrate(modid) }
    }

    private var isRegistered = false

    fun register() {
        if (isRegistered) return
        registerEventListeners(MOD_BUS)
        isRegistered = true
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> TagKey<T>.provider(): ProviderType<out RegistrateTagsProvider<T>> =
        when (registry) {
            Registries.BLOCK -> ProviderType.BLOCK_TAGS
            Registries.ITEM -> ProviderType.ITEM_TAGS
            else -> throw IllegalArgumentException("no tag provider known for registry ${registry.location()}")
        } as ProviderType<out RegistrateTagsProvider<T>>

    fun <T : Any> TagKey<T>.addOptional(id: ResourceLocation) {
        addDataGenerator(provider()) {
            it.addTag(this).addOptional(id)
        }
    }

    fun <T : Any> TagKey<T>.addOptional(tag: TagKey<T>) {
        addDataGenerator(provider()) {
            it.addTag(this).addOptionalTag(tag)
        }
    }

    fun dyedBlock(color: DyeColor) = dyedBlock(color, modid, ::Block)

    fun <T : Block> dyedBlock(
        color: DyeColor,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ) = dyedBlock(color, modid, factory)

    fun dyedBlock(
        color: DyeColor,
        mod: String,
    ) = dyedBlock(color, mod, ::Block)

    fun <T : Block> dyedBlock(
        color: DyeColor,
        mod: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, DyedRegistrate> {
        val name = currentName()
        return entry(name) { callback ->
            DyedBlockBuilder(
                this,
                this,
                color,
                mod,
                name,
                callback,
                factory,
                BlockBehaviour.Properties::of,
            ).apply {
                defaultBlockstate()
                defaultLoot()
                defaultLang()
                tagDyed()
            }
        }
    }

    fun <T : Item> dyedItem(
        color: DyeColor,
        mod: String,
        factory: NonNullFunction<Item.Properties, T>,
    ): ItemBuilder<T, DyedRegistrate> {
        val name = currentName()
        return entry(name) { callback ->
            DyedItemBuilder(
                this,
                this,
                color,
                mod,
                name,
                callback,
                factory,
            ).apply {
                defaultModel()
                defaultLang()
                tagDyed()
            }
        }
    }

    fun <T : Item> dyedItem(
        color: DyeColor,
        factory: NonNullFunction<Item.Properties, T>,
    ) = dyedItem(color, modid, factory)
}
