package com.possible_triangle.dye_the_world.index

import com.illusivesoulworks.comforts.common.block.BaseComfortsBlock
import com.illusivesoulworks.comforts.common.block.HammockBlock
import com.illusivesoulworks.comforts.common.block.SleepingBagBlock
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.COMFORTS
import com.possible_triangle.dye_the_world.KotlinRegistrate
import com.possible_triangle.dye_the_world.blockByDye
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyeingRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue

object DyedComforts {

    private val REGISTRATE = KotlinRegistrate(COMFORTS)

    val SLEEPING_BAGS = dyesFor(COMFORTS).associateWith { dye ->
        REGISTRATE.`object`("sleeping_bag_${dye}")
            .block { SleepingBagBlock(dye) }
            .lang("${dye.translation} Sleeping Bag")
            .recipe { c, p -> dye.sleepingBagRecipe(c, p) }
            .cloth()
            .register()
    }

    val HAMMOCKS = dyesFor(COMFORTS).associateWith { dye ->
        REGISTRATE.`object`("hammock_${dye}")
            .block { HammockBlock(dye) }
            .lang("${dye.translation} Hammock")
            .recipe { c, p -> dye.hammockRecipe(c, p) }
            .cloth()
            .register()
    }

    private fun <T : BaseComfortsBlock, P> BlockBuilder<T, P>.cloth() = apply {
        blockstate(::clothBlockState)
        loot { t, b ->
            val isHead = LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(BedBlock.PART, BedPart.HEAD)
            )

            val pool = t.applyExplosionDecay(b, LootPool.lootPool())
                .add(LootItem.lootTableItem(b))
                .setRolls(ConstantValue.exactly(1.0F))
                .`when`(isHead)

            t.add(b, LootTable.lootTable().withPool(pool))
        }

        item()
            .model { c, p -> p.generated(c, Constants.MOD_ID.createId("item/comforts/${c.name}")) }
            .register()
    }

    private fun clothBlockState(
        context: DataGenContext<Block, out BaseComfortsBlock>,
        provider: RegistrateBlockstateProvider,
    ) {
        val dye = context.get().color
        val model = provider.models().getBuilder("block/${dye}_cloth")
            .texture("particle", Constants.Mods.DYE_DEPOT.createId("block/${dye}_wool"))

        provider.simpleBlock(context.get(), model)
    }

    private fun DyeColor.hammockRecipe(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider
    ) {
        dyeingRecipe(context, provider, DyedTags.HAMMOCKS) {
            group("comforts:hammock_dyed")
        }

        val wool = blockByDye(this, "wool")
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
            .pattern("S/S")
            .pattern("S#S")
            .pattern("S/S")
            .define('S', Items.STRING)
            .define('/', Items.STICK)
            .define('#', wool)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .group("comforts:sleeping_bags")
            .save(provider)
    }

    private fun DyeColor.sleepingBagRecipe(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider
    ) {
        dyeingRecipe(context, provider, DyedTags.SLEEPING_BAGS) {
            group("comforts:sleeping_bags_dyed")
        }

        val wool = blockByDye(this, "wool")
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
            .pattern("###")
            .define('#', wool)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .group("comforts:hammocks")
            .save(provider)
    }

    fun register() {
        REGISTRATE.register()
    }

}
