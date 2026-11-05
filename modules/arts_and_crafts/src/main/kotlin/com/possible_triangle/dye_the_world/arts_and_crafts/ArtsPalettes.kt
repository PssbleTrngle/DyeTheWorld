package com.possible_triangle.dye_the_world.arts_and_crafts

import com.possible_triangle.dye_the_world.aeronautics.AERONAUTICS
import com.possible_triangle.dye_the_world.aeronautics.DyedAeronautics
import com.possible_triangle.dye_the_world.alexs_caves.ALEXS_CAVES
import com.possible_triangle.dye_the_world.alexs_caves.DyedAlexsCaves
import com.possible_triangle.dye_the_world.bits_n_bobs.BITS_N_BOBS
import com.possible_triangle.dye_the_world.bits_n_bobs.DyedBitsNBobs
import com.possible_triangle.dye_the_world.botany_pots.BOTANY_POTS
import com.possible_triangle.dye_the_world.botany_pots.DyedBotanyPots
import com.possible_triangle.dye_the_world.clayworks.CLAYWORKS
import com.possible_triangle.dye_the_world.clayworks.DyedClayworks
import com.possible_triangle.dye_the_world.comforts.COMFORTS
import com.possible_triangle.dye_the_world.comforts.DyedComforts
import com.possible_triangle.dye_the_world.connected_glass.CONNECTED_GLASS
import com.possible_triangle.dye_the_world.connected_glass.DyedConnectedGlass
import com.possible_triangle.dye_the_world.create.CREATE
import com.possible_triangle.dye_the_world.create.DyedCreate
import com.possible_triangle.dye_the_world.create_deco.CREATE_DECO
import com.possible_triangle.dye_the_world.create_deco.DyedCreateDeco
import com.possible_triangle.dye_the_world.create_interiors.CREATE_INTERIORS
import com.possible_triangle.dye_the_world.create_interiors.DyedCreateInteriors
import com.possible_triangle.dye_the_world.extensions.ifLoaded
import com.possible_triangle.dye_the_world.farmers_delight.DyedFarmersDelight
import com.possible_triangle.dye_the_world.farmers_delight.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.more_concrete.DyedMoreConcrete
import com.possible_triangle.dye_the_world.more_concrete.MORE_CONCRETE
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.ItemLike

object ArtsPalettes {
    private fun fam(
        mod: String,
        suffix: String,
        blocks: Map<DyeColor, ItemLike>,
        base: String? = null,
    ) = PaintPalettes.add("${mod}_$suffix", blocks, suffix, mod, base)

    fun registerAll() {
        vanilla()

        ifLoaded(BITS_N_BOBS) {
            fam(BITS_N_BOBS, "chair", DyedBitsNBobs.CHAIRS)
            fam(BITS_N_BOBS, "nixie_board", DyedBitsNBobs.NIXIE_BOARDS)
            fam(BITS_N_BOBS, "large_nixie_tube", DyedBitsNBobs.LARGE_NIXIE_TUBES)
        }

        ifLoaded(BOTANY_POTS) {
            listOf(
                DyedBotanyPots.TERRACOTTA_POTS,
                DyedBotanyPots.CONCRETE_POTS,
                DyedBotanyPots.GLAZED_TERRACOTTA_POTS,
            ).forEach { pots ->
                fam(BOTANY_POTS, "botany_pots", pots.basic)
                fam(BOTANY_POTS, "hopper_botany_pots", pots.hopper)
                fam(BOTANY_POTS, "waxed_botany_pots", pots.waxed)
            }
        }

        ifLoaded(CLAYWORKS) {
            fam(CLAYWORKS, "terracotta_bricks", DyedClayworks.TERRACOTTA_BRICKS)
            fam(CLAYWORKS, "terracotta_brick_slab", DyedClayworks.TERRACOTTA_BRICK_SLABS)
            fam(CLAYWORKS, "terracotta_brick_stairs", DyedClayworks.TERRACOTTA_BRICK_STAIRS)
            fam(CLAYWORKS, "terracotta_brick_wall", DyedClayworks.TERRACOTTA_BRICK_WALLS)
            fam(CLAYWORKS, "chiseled_terracotta_bricks", DyedClayworks.CHISELED_TERRACOTTA_BRICKS)
            fam(CLAYWORKS, "terracotta_slab", DyedClayworks.TERRACOTTA_SLABS)
            fam(CLAYWORKS, "terracotta_stairs", DyedClayworks.TERRACOTTA_STAIRS)
            fam(CLAYWORKS, "terracotta_wall", DyedClayworks.TERRACOTTA_WALLS)
            fam(CLAYWORKS, "decorated_pot", DyedClayworks.DECORATED_POTS, base = "minecraft:decorated_pot")
            fam(CLAYWORKS, "stained_glass_door", DyedClayworks.STAINED_GLASS_DOORS)
            fam(CLAYWORKS, "stained_glass_trapdoor", DyedClayworks.STAINED_GLASS_TRAPDOORS)
        }

        ifLoaded(COMFORTS) {
            fam(COMFORTS, "sleeping_bag", DyedComforts.SLEEPING_BAGS)
            fam(COMFORTS, "hammock", DyedComforts.HAMMOCKS)
        }

        ifLoaded(CREATE) {
            fam(CREATE, "nixie_tube", DyedCreate.NIXIE_TUBES)
            fam(CREATE, "toolbox", DyedCreate.TOOLBOXES)
            fam(CREATE, "seat", DyedCreate.SEATS)
            fam(CREATE, "sail", DyedCreate.SAILS)
            fam(CREATE, "valve_handle", DyedCreate.VALVE_HANDLES)
            fam(CREATE, "table_cloth", DyedCreate.TABLE_CLOTHS)
            fam(CREATE, "postbox", DyedCreate.POST_BOXES)
        }

        ifLoaded(CREATE_DECO) {
            fam(CREATE_DECO, "shipping_container", DyedCreateDeco.SHIPPING_CONTAINERS)
            fam(CREATE_DECO, "placard", DyedCreateDeco.PLACARDS)
        }

        ifLoaded(CREATE_INTERIORS) {
            fam(CREATE_INTERIORS, "chair", DyedCreateInteriors.CHAIRS)
            fam(CREATE_INTERIORS, "floor_chair", DyedCreateInteriors.FLOOR_CHAIRS)
            fam(CREATE_INTERIORS, "cushion", DyedCreateInteriors.CUSHIONS)
        }

        ifLoaded(AERONAUTICS) {
            fam(AERONAUTICS, "envelope", DyedAeronautics.ENVELOPES)
            fam(AERONAUTICS, "envelope_encased_shaft", DyedAeronautics.ENVELOPES_SHAFTS)
        }

        ifLoaded(ALEXS_CAVES) {
            fam(ALEXS_CAVES, "radon_lamp", DyedAlexsCaves.RADON_LAMPS)
        }

        ifLoaded(MORE_CONCRETE) {
            fam(MORE_CONCRETE, "concrete_slab", DyedMoreConcrete.CONCRETE_SLABS)
            fam(MORE_CONCRETE, "concrete_stairs", DyedMoreConcrete.CONCRETE_STAIRS)
            fam(MORE_CONCRETE, "concrete_wall", DyedMoreConcrete.CONCRETE_WALLS)
            fam(MORE_CONCRETE, "concrete_fence", DyedMoreConcrete.CONCRETE_FENCES)
            fam(MORE_CONCRETE, "concrete_fence_gate", DyedMoreConcrete.CONCRETE_FENCE_GATES)
            fam(MORE_CONCRETE, "concrete_button", DyedMoreConcrete.CONCRETE_BUTTONS)
            fam(MORE_CONCRETE, "concrete_lever", DyedMoreConcrete.CONCRETE_LEVERS)
            fam(MORE_CONCRETE, "concrete_pressure_plate", DyedMoreConcrete.CONCRETE_PRESSURE_PLATES)
        }

        ifLoaded(CONNECTED_GLASS) {
            fam(CONNECTED_GLASS, "borderless_glass", DyedConnectedGlass.BORDERLESS)
            fam(CONNECTED_GLASS, "borderless_glass_pane", DyedConnectedGlass.BORDERLESS_PANES)
            fam(CONNECTED_GLASS, "scratched_glass", DyedConnectedGlass.SCRATCHED)
            fam(CONNECTED_GLASS, "scratched_glass_pane", DyedConnectedGlass.SCRATCHED_PANES)
            fam(CONNECTED_GLASS, "clear_glass", DyedConnectedGlass.CLEAR)
            fam(CONNECTED_GLASS, "clear_glass_pane", DyedConnectedGlass.CLEAR_PANES)
            fam(CONNECTED_GLASS, "tinted_borderless_glass", DyedConnectedGlass.TINTED)
        }

        ifLoaded(FARMERS_DELIGHT) {
            fam(FARMERS_DELIGHT, "canvas_sign", DyedFarmersDelight.CANVAS_SIGNS)
            fam(FARMERS_DELIGHT, "canvas_wall_sign", DyedFarmersDelight.CANVAS_WALL_SIGNS)
            fam(FARMERS_DELIGHT, "hanging_canvas_sign", DyedFarmersDelight.HANGING_CANVAS_SIGNS)
            fam(FARMERS_DELIGHT, "wall_hanging_canvas_sign", DyedFarmersDelight.HANGING_CANVAS_WALL_SIGNS)
        }
    }

    private fun vanilla() {
        PaintPalettes.addVanilla("vanilla_wool", "wool")
        PaintPalettes.addVanilla("vanilla_carpet", "carpet")
        PaintPalettes.addVanilla("vanilla_terracotta", "terracotta", base = "minecraft:terracotta")
        PaintPalettes.addVanilla("vanilla_glazed_terracotta", "glazed_terracotta")
        PaintPalettes.addVanilla("vanilla_concrete", "concrete")
        PaintPalettes.addVanilla("vanilla_concrete_powder", "concrete_powder")
        PaintPalettes.addVanilla("vanilla_stained_glass", "stained_glass", base = "minecraft:glass")
        PaintPalettes.addVanilla("vanilla_stained_glass_pane", "stained_glass_pane", base = "minecraft:glass_pane")
        PaintPalettes.addVanilla("vanilla_candle", "candle", base = "minecraft:candle")
        PaintPalettes.addVanilla("vanilla_bed", "bed")
    }
}
