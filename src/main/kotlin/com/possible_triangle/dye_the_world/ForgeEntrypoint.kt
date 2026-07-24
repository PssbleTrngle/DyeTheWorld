package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.compat.CreateCompat
import com.possible_triangle.dye_the_world.compat.VanillaBackportsCompat
import com.possible_triangle.dye_the_world.data.createDyeRecipes
import com.possible_triangle.dye_the_world.data.generateGlassShardLoot
import com.possible_triangle.dye_the_world.data.generatePackMetadata
import com.possible_triangle.dye_the_world.data.generateTags
import com.possible_triangle.dye_the_world.data.registerExistingFiles
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.ifLoaded
import com.possible_triangle.dye_the_world.extensions.isLoaded
import com.possible_triangle.dye_the_world.index.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.minecraftforge.data.loading.DatagenModLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {
    val REGISTRATE = DyedRegistrate.create(Constants.MOD_ID)

    init {
        REGISTRATE.register()

        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }

        if (isLoaded(Constants.Mods.QUARK)) {
            DyedQuark.register()
        } else {
            stubLootCondition(Constants.Mods.QUARK.createId("flag"))
            stubRecipeCondition(Constants.Mods.QUARK.createId("flag"))
        }

        ifLoaded(Constants.Mods.CLAYWORKS) {
            DyedClayworks.register()
        }

        ifLoaded(Constants.Mods.FARMERS_DELIGHT) {
            DyedDelight.register()
        }

        ifLoaded(Constants.Mods.ALEXS_CAVES) {
            DyedCaves.register()
        }

        ifLoaded(Constants.Mods.DOMESTICATION_INNOVATION) {
            DyedDomestication.register()
        }

        ifLoaded(Constants.Mods.UPGRADE_AQUATIC) {
            DyedAquatic.register()
        }

        ifLoaded(Constants.Mods.MORE_CONCRETE) {
            DyedConcrete.register()
        }

        ifLoaded(Constants.Mods.CREATE) {
            CreateCompat.registerDyes()
        }

        ifLoaded(Constants.Mods.VANILLA_BACKPORT) {
            DyedVanillaBackport.register()
            MOD_BUS.addListener { _: FMLClientSetupEvent -> VanillaBackportsCompat.registerHarnessLayers() }
        }

        ifLoaded(Constants.Mods.SPELUNKERY) {
            DyedSpelunkery.register()
        }

        ifLoaded(Constants.Mods.TWIGS) {
            DyedTwigs.register()
        }

        if (DatagenModLoader.isRunningDataGen()) {
            Constants.LOGGER.debug("registering datagen")

            REGISTRATE.registerExistingFiles()

            REGISTRATE.generateTags()
            REGISTRATE.generatePackMetadata()
            REGISTRATE.createDyeRecipes()
            generateGlassShardLoot()

            // These are blocks & Items which are automatically added for all dye colors, included modded ones.
            // Therefore, they only lack assets & data files, which have to be generated, but do not need to be registered.
            DyedSupplementaries.register()
            DyedComforts.register()
            DyedCreate.register()
            DyedCreateInterior.register()
            DyedCreateDeco.register()
            DyedRailways.register()
            DyedChalk.registerDatagen()
            DyedWaystones.register()
            DyedSnowySpirit.register()
            DyedElevators.register()
            DyedConnectedGlass.register()
            DyedBotanyPots.register()
            DyedNumismatics.register()
        }
    }
}
