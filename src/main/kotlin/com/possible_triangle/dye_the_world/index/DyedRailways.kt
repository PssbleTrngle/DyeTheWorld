package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_RAILWAYS
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllItems
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe
import com.simibubi.create.content.kinetics.saw.CuttingRecipe
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder
import net.minecraft.world.item.Item
import net.neoforged.neoforge.common.Tags

object DyedRailways {
    private val REGISTRATE = DyedRegistrate.create(CREATE_RAILWAYS)

    private val DYES = dyesFor(CREATE_RAILWAYS)

    val INCOMPLETE_CONDUCTOR_CAPS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_incomplete_conductor_cap")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Incomplete Conductor's Cap")
                .model { context, provider ->
                    provider
                        .withExistingParent(context.name, CREATE_RAILWAYS.createId("item/incomplete_conductor_cap"))
                        .texture("cap", CREATE_RAILWAYS.createId("entity/caps/${dye}_conductor_cap"))
                }.register()
        }

    val CONDUCTOR_CAPS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_conductor_cap")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Conductor's Cap")
                .optionalTag(DyedTags.Items.CONDUCTOR_CAPS)
                .model { context, provider ->
                    provider
                        .withExistingParent(context.name, CREATE_RAILWAYS.createId("item/conductor_cap"))
                        .texture("cap", CREATE_RAILWAYS.createId("entity/caps/${dye}_conductor_cap"))
                }.recipe { context, provider ->
                    SequencedAssemblyRecipeBuilder(context.id)
                        .loops(1)
                        .transitionTo(INCOMPLETE_CONDUCTOR_CAPS[dye]!!)
                        .addOutput(context.get(), 1F)
                        .require(dye.blockOf("wool"))
                        .addStep(::CuttingRecipe) { it }
                        .addStep(::DeployerApplicationRecipe) { it.require(AllItems.PRECISION_MECHANISM) }
                        .addStep(::DeployerApplicationRecipe) { it.require(Tags.Items.STRINGS) }
                        .build(provider)

                    provider.dyeingRecipe(dye, DyedTags.Items.CONDUCTOR_CAPS, context)
                }.register()
        }

    fun register() {
        REGISTRATE.register()
    }
}
