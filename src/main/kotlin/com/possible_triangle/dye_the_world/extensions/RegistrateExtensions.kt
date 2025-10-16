package com.possible_triangle.dye_the_world.extensions

import com.possible_triangle.dye_the_world.ForgeEntrypoint
import com.possible_triangle.dye_the_world.data.CustomRegistrateLangProvider
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BlockEntityBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity

fun <T : Item, P> ItemBuilder<T, P>.optionalTag(tag: TagKey<Item>) = apply {
    ForgeEntrypoint.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS) {
        it.addTag(tag).addOptional(owner.modid.createId(name))
    }
}

fun <T : Block, P> BlockBuilder<T, P>.optionalTag(tag: TagKey<Block>) = apply {
    ForgeEntrypoint.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS) {
        it.addTag(tag).addOptional(owner.modid.createId(name))
    }
}

fun <T : Block, P> BlockBuilder<T, P>.withItem(
    factory: (T, Item.Properties) -> BlockItem = ::BlockItem,
    block: ItemBuilder<BlockItem, BlockBuilder<T, P>>.() -> Unit = {},
): BlockBuilder<T, P> {
    return item(factory)
        .apply(block)
        .build()
}

fun <T : BlockEntity, P> BlockEntityBuilder<T, P>.validBlocks(
    values: Collection<NonNullSupplier<out Block>>,
): BlockEntityBuilder<T, P> {
    return validBlocks(*values.toTypedArray())
}

val DE_LANG = CustomRegistrateLangProvider.providerType("de_de")

fun <T : Item, P> ItemBuilder<T, P>.germanLang(translation: String) = setData(DE_LANG) { context, provider ->
    provider.add(context.get(), translation)
}

fun <T : Block, P> BlockBuilder<T, P>.germanLang(translation: String) = setData(DE_LANG) { context, provider ->
    provider.add(context.get(), translation)
}

fun <T : Item, P> ItemBuilder<T, P>.optionalTab(vararg keys: ResourceKey<CreativeModeTab>, condition: () -> Boolean) =
    apply {
        keys.forEach { key ->
            tab(key) { item, it ->
                if (condition()) it.accept(item)
            }
        }
    }
