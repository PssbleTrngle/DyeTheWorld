package com.possible_triangle.dye_the_world.extensions

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.Builder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.DataIngredient
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.neoforged.fml.ModList
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun isLoaded(modid: String) = ModList.get().isLoaded(modid)

inline fun ifLoaded(
    modid: String,
    block: () -> Unit,
) {
    if (isLoaded(modid)) block()
}

val Direction.yRot: Int
    get() =
        when (this) {
            Direction.EAST -> 90
            Direction.SOUTH -> 180
            Direction.WEST -> 270
            else -> 0
        }

fun <T : Any> Registry<T>.getOrThrow(id: ResourceLocation): T {
    val key = ResourceKey.create(key(), id)
    return getOrThrow(key)
}

fun String.createId(path: String) = ResourceLocation.fromNamespaceAndPath(this, path)

val <R, T : R, P, S : Builder<R, T, P, S>> Builder<R, T, P, S>.namespace
    get(): String {
        return when (val parent = parent) {
            is AbstractRegistrate<*> -> parent.modid
            is Builder<*, *, *, *> -> parent.namespace
            else -> error("Unable to locate registrate ancestor")
        }
    }

fun BlockStateProvider.createVariant(
    block: NonNullSupplier<out Block>,
    vararg ignored: Property<*>,
    mapper: (BlockState) -> ConfiguredModel.Builder<*>,
) {
    getVariantBuilder(block.get()).forAllStatesExcept(
        { mapper(it).build() },
        BlockStateProperties.WATERLOGGED,
        *ignored,
    )
}

fun NonNullSupplier<out ItemLike>.asIngredient() = DataIngredient.items(this)

fun <K, V> Map<V, K>.inverse() = map { it.value to it.key }.toMap()

@Suppress("UNCHECKED_CAST")
fun <T : RecipeBuilder> T.unlockedBy(item: ItemLike): T {
    val id = BuiltInRegistries.ITEM.getKeyOrNull(item.asItem()) ?: error("unknown item")
    return unlockedBy("has_${id.path}", RegistrateRecipeProvider.has(item)) as T
}

@Suppress("UNCHECKED_CAST")
fun <T : RecipeBuilder> T.unlockedBy(tag: TagKey<Item>): T = unlockedBy("has_${tag.location.path}", RegistrateRecipeProvider.has(tag)) as T

fun ShapedRecipeBuilder.defineUnlocking(
    key: Char,
    item: ItemLike,
) = define(key, item).unlockedBy(item)

fun ShapedRecipeBuilder.defineUnlocking(
    key: Char,
    tag: TagKey<Item>,
) = define(key, tag).unlockedBy(tag)

fun ShapelessRecipeBuilder.requiresUnlocking(item: ItemLike) = requires(item).unlockedBy(item)

fun ShapelessRecipeBuilder.requiresUnlocking(tag: TagKey<Item>) = requires(tag).unlockedBy(tag)

fun matchesState(
    block: Block,
    factory: StatePropertiesPredicate.Builder.() -> Unit,
): LootItemCondition.Builder =
    LootItemBlockStatePropertyCondition
        .hasBlockStateProperties(block)
        .setProperties(StatePropertiesPredicate.Builder.properties().apply(factory))
