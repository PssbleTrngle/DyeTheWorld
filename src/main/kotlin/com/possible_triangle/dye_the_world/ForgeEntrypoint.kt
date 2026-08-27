package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.compat.ChalkCompat
import com.possible_triangle.dye_the_world.compat.CreateCompat
import com.possible_triangle.dye_the_world.compat.SleepThighCompat
import com.possible_triangle.dye_the_world.compat.VanillaBackportsCompat
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.ifLoaded
import com.possible_triangle.dye_the_world.index.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.data.loading.DatagenModLoader
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {
    val REGISTRATE = DyedRegistrate.create(Constants.MOD_ID)

    init {
        Constants.LOGGER.debug("setup")
        REGISTRATE.register()

        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }

        ifLoaded(Constants.Mods.QUARK) {
            DyedQuark.register()
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

        ifLoaded(Constants.Mods.SLEEP_TIGHT) {
            SleepThighCompat.init()
        }

        ifLoaded(Constants.Mods.WINDSWEPT) {
            DyedWindswept.register()
        }

        ifLoaded(Constants.Mods.CHALK) {
            MOD_BUS.addListener(ChalkCompat::addCreativeTabEntries)
        }

        if (DatagenModLoader.isRunningDataGen()) {
            Constants.LOGGER.debug("registering datagen")

            REGISTRATE.registerExistingFiles()

            REGISTRATE.generateTags()
            REGISTRATE.generatePackMetadata()
            REGISTRATE.createDyeRecipes()
            REGISTRATE.generateGlassShardLoot()

            // These are blocks & Items which are automatically added for all dye colors, included modded ones.
            // Therefore, they only lack assets & data files, which have to be generated, but do not need to be registered.
            DyedSupplementaries.register()
            DyedComforts.register()
            DyedCreate.register()
            DyedCreateInterior.register()
            DyedCreateDeco.register()
            DyedRailways.register()
            DyedWaystones.register()
            DyedSnowySpirit.register()
            DyedElevators.register()
            DyedConnectedGlass.register()
            DyedBotanyPots.register()
            DyedSimulated.register()
            DyedAeronautics.register()
            DyedNumismatics.register()
            DyedSleepTight.register()
            DyedBits.register()
            DyedChalk.register()
        }
    }
}
