package com.possible_triangle.dye_the_world.data

import com.google.common.base.Preconditions
import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SNOWY_SPIRIT
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder
import net.neoforged.neoforge.client.model.generators.BlockModelProvider
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder
import net.neoforged.neoforge.client.model.generators.ModelBuilder
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.client.model.generators.ModelProvider
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper

private class GlowLightsLoaderBuilder(
    model: BlockModelBuilder,
    fileHelper: ExistingFileHelper,
) : CustomLoaderBuilder<BlockModelBuilder>(
    SNOWY_SPIRIT.createId("glow_lights"),
    model,
    fileHelper,
    false
) {
    private var parent: ModelFile? = null
    private val textures = hashMapOf<String, ResourceLocation>()

    fun parent(value: ModelFile) = apply {
        value.assertExistence()
        parent = value
    }

    fun texture(key: String, value: ResourceLocation) = apply {
        check(existingFileHelper.exists(value, ModelProvider.TEXTURE)) {
            "Texture $value does not exist in any known resource pack"
        }
        textures[key] = value
    }

    override fun toJson(json: JsonObject) = json.apply {
        addProperty("loader", loaderId.toString())
        val overlay = JsonObject().apply {
            parent?.let { addProperty("parent", it.uncheckedLocation.toString()) }
            add("textures", JsonObject().apply {
                textures.forEach { (key, value) ->
                    addProperty(key, value.toString())
                }
            })
        }
        add("overlay", overlay)
    }
}

fun <T : Block, P> BlockBuilder<T, P>.glowLightsBlockstate() = blockstate { context, provider ->
    val model = provider.models().getBuilder(context.name)
        .customLoader(::GlowLightsLoaderBuilder)
        .parent(provider.models().getExistingFile(SNOWY_SPIRIT.createId("block/template_glow_lights")))
        .texture("all", Constants.MOD_ID.createId("block/$SNOWY_SPIRIT/glow_lights/$dye"))
        .end()
    provider.simpleBlock(context.get(), model)
}

fun <T : Item, P> ItemBuilder<T, P>.glowLightsModel() = model { context, provider ->
    provider.generated(context, Constants.MOD_ID.createId("item/$SNOWY_SPIRIT/glow_lights/$dye"))
}

fun <T : Item, P> ItemBuilder<T, P>.glowLightsRecipe() = recipe { context, provider ->
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, context.get())
        .group("glow_lights")
        .requiresUnlocking(Items.GLOWSTONE_DUST)
        .requires(dye.tag)
        .requiresUnlocking(Items.AMETHYST_SHARD)
        .requires(Tags.Items.STRINGS)
        .save(provider)
}