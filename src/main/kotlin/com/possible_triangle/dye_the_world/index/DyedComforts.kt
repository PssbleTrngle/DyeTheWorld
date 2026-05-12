package com.possible_triangle.dye_the_world.index

import com.illusivesoulworks.comforts.common.block.BaseComfortsBlock
import com.illusivesoulworks.comforts.common.block.HammockBlock
import com.illusivesoulworks.comforts.common.block.SleepingBagBlock
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.COMFORTS
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue

object DyedComforts {
    private val REGISTRATE = DyedRegistrate.create(COMFORTS)

    val SLEEPING_BAGS =
        dyesFor(COMFORTS).associateWith { dye ->
            REGISTRATE
                .`object`("sleeping_bag_$dye")
                .dyedBlock(dye) { SleepingBagBlock(dye) }
                .lang("${dye.translation} Sleeping Bag")
                .germanLang("${dye.germanTranslation(Genus.M)} Schlafsack")
                .withItem {
                    sleepingBagRecipe()
                }.clothTransforms()
                .register()
        }

    val HAMMOCKS =
        dyesFor(COMFORTS).associateWith { dye ->
            REGISTRATE
                .`object`("hammock_$dye")
                .dyedBlock(dye) { HammockBlock(dye) }
                .lang("${dye.translation} Hammock")
                .germanLang("${dye.germanTranslation(Genus.M)} Hängemattenstoff")
                .withItem {
                    hammockRecipe()
                }.clothTransforms()
                .register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

private fun <T : BaseComfortsBlock, P> BlockBuilder<T, P>.clothTransforms() =
    apply {
        clothBlockState()
        loot { t, b ->
            val isHead =
                matchesState(b) {
                    hasProperty(BedBlock.PART, BedPart.HEAD)
                }

            val pool =
                t
                    .applyExplosionDecay(b, LootPool.lootPool())
                    .add(LootItem.lootTableItem(b))
                    .setRolls(ConstantValue.exactly(1.0F))
                    .`when`(isHead)

            t.add(b, LootTable.lootTable().withPool(pool))
        }

        item()
            .model { c, p -> p.generated(c, Constants.MOD_ID.createId("item/comforts/${c.name}")) }
            .register()
    }

private fun <T : BaseComfortsBlock, P> BlockBuilder<T, P>.clothBlockState() =
    blockstate { context, provider ->
        val dye = context.get().color
        val model =
            provider
                .models()
                .getBuilder("block/${dye}_cloth")
                .texture("particle", Constants.Mods.DYE_DEPOT.createId("block/${dye}_wool"))

        provider.simpleBlock(context.get(), model)
    }

private fun <T : Item, P> ItemBuilder<T, P>.hammockRecipe() =
    recipe { context, provider ->
        val wool = dye.blockOf("wool")
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
            .pattern("S/S")
            .pattern("S#S")
            .pattern("S/S")
            .define('S', Items.STRING)
            .define('/', Items.STICK)
            .define('#', wool)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .group("comforts:sleeping_bags")
            .save(provider)

        provider.dyeingRecipe(dye, DyedTags.Items.HAMMOCKS, context) {
            group("comforts:hammock_dyed")
        }
    }

private fun <T : Item, P> ItemBuilder<T, P>.sleepingBagRecipe() =
    recipe { context, provider ->
        val wool = dye.blockOf("wool")
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
            .pattern("###")
            .define('#', wool)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .group("comforts:hammocks")
            .save(provider)

        provider.dyeingRecipe(dye, DyedTags.Items.SLEEPING_BAGS, context) {
            group("comforts:sleeping_bags_dyed")
        }
    }
