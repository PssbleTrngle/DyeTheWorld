import {
  createLogger,
  PackLoader,
  type IdInput,
} from "@pssbletrngle/data-modifier";
import type { ItemId } from "@pssbletrngle/data-modifier/generated";
import { createResolver } from "@pssbletrngle/pack-resolver";
import { createDefaultMergers } from "@pssbletrngle/resource-merger";
import { copyFileSync, readFileSync } from "fs";
import { resolve } from "path";

const logger = createLogger();

const packFormat = 15;
const loader = new PackLoader(logger, { packFormat });
await loader.loadRegistryDump(createResolver({ from: resolve("dump") }));

const PACK_NAMESPACE = "dye_the_world";
const tab = loader.tabs.create(
  { namespace: PACK_NAMESPACE, path: "tab" },
  {
    icon: "dye_depot:teal_dye_basket",
    name: "Dye Depot",
  }
);

const colors = [
  "maroon",
  "rose",
  "coral",
  "ginger",
  "tan",
  "beige",
  "amber",
  "olive",
  "forest",
  "verdant",
  "teal",
  "mint",
  "aqua",
  "slate",
  "navy",
  "indigo",
];

logger.info("generating pack...");

function prepend(color: string, base: string) {
  return `${color}_${base}`;
}

function append(color: string, base: string) {
  return `${base}_${color}`;
}

function replace(color: string, base: string) {
  return base.replace("$color", color);
}

let previous: IdInput<ItemId> | undefined = undefined;

function requireItem(id: IdInput) {
  loader.registries.validateEntry("minecraft:item", id);
  return id as ItemId;
}

function addColored(
  namespace: string,
  base: string,
  mod: string = namespace,
  idFactory: (color: string, base: string) => string = prepend
) {
  const ids = colors.map<IdInput<ItemId>>((color) => {
    return requireItem({ namespace, path: idFactory(color, base) });
  });
  loader.tabs.add(tab, ids, {
    file: { namespace: PACK_NAMESPACE, path: mod },
    mods: [mod],
    after: previous,
  });
  logger.info(`  added ${base}s from ${mod}`);

  previous = ids[ids.length - 1];
}

addColored("dye_depot", "dye");
addColored("dye_depot", "dye_basket");
addColored("dye_depot", "wool");
addColored("dye_depot", "carpet");
addColored("dye_depot", "banner");
addColored("supplementaries", "flag", undefined, append);
addColored("dye_depot", "bed");
addColored("dye_depot", "shulker_box");

addColored("dye_depot", "candle");
addColored("supplementaries", "candle_holder", undefined, append);
addColored("suppsquared", "gold_candle_holder", undefined, append);

addColored("dye_depot", "stained_glass");
addColored("dye_depot", "stained_glass_pane");
addColored("oreganized", "crystal_glass");
addColored("oreganized", "crystal_glass_pane");
addColored("dye_the_world", "framed_glass", "quark");
addColored("dye_the_world", "framed_glass_pane", "quark");
addColored(
  "connectedglass",
  "borderless_glass_$color_pane",
  undefined,
  replace
);
addColored("connectedglass", "scratched_glass_$color_pane", undefined, replace);
addColored("connectedglass", "clear_glass_$color_pane", undefined, replace);
addColored("connectedglass", "borderless_glass", undefined, append);
addColored("connectedglass", "scratched_glass", undefined, append);
addColored("connectedglass", "clear_glass", undefined, append);
addColored("connectedglass", "tinted_borderless_glass", undefined, append);

addColored("dye_the_world", "shingles", "quark");
addColored("dye_the_world", "shingles_slab", "quark");
addColored("dye_the_world", "shingles_stairs", "quark");

addColored("dye_the_world", "terracotta_bricks", "clayworks");
addColored("dye_the_world", "terracotta_brick_slab", "clayworks");
addColored("dye_the_world", "terracotta_brick_stairs", "clayworks");
addColored("dye_the_world", "terracotta_brick_wall", "clayworks");
addColored(
  "dye_the_world",
  "chiseled_$color_terracotta_bricks",
  "clayworks",
  replace
);
addColored("dye_depot", "terracotta");
addColored("dye_the_world", "terracotta_slab", "clayworks");
addColored("dye_the_world", "terracotta_stairs", "clayworks");
addColored("dye_the_world", "terracotta_wall", "clayworks");
addColored("dye_depot", "glazed_terracotta");
addColored("dye_the_world", "decorated_pot", "clayworks");

addColored("dye_depot", "concrete_powder");
addColored("oreganized", "waxed_$color_concrete_powder", undefined, replace);
addColored("dye_depot", "concrete");
addColored("dye_the_world", "concrete_slab", "moreconcrete");
addColored("dye_the_world", "concrete_stairs", "moreconcrete");
addColored("dye_the_world", "concrete_wall", "moreconcrete");
addColored("dye_the_world", "concrete_fence", "moreconcrete");
addColored("dye_the_world", "concrete_fence_gate", "moreconcrete");
addColored("dye_the_world", "concrete_button", "moreconcrete");
addColored("dye_the_world", "concrete_lever", "moreconcrete");
addColored("dye_the_world", "concrete_pressure_plate", "moreconcrete");

addColored("suppsquared", "sack", undefined, append);
addColored("supplementaries", "present", undefined, append);
addColored("supplementaries", "trapped_present", undefined, append);
addColored("supplementaries", "awning", undefined, append);

addColored("comforts", "sleeping_bag", undefined, append);
addColored("comforts", "hammock", undefined, append);
addColored("dye_the_world", "bedroll", "upgrade_aquatic");

addColored("dye_the_world", "quark_stool");

addColored("chalk", "chalk");

addColored("create", "valve_handle");
addColored("create", "toolbox");
addColored("create", "postbox");
addColored("railways", "conductor_cap");
addColored("createdeco", "shipping_container");
addColored("createdeco", "placard");

addColored("create", "table_cloth");
addColored("create", "seat");
addColored("interiors", "floor_chair");
addColored("interiors", "chair");
addColored("interiors", "cushion");

addColored("dye_the_world", "sofa", "another_furniture");
addColored("dye_the_world", "sofa", "stool");
addColored("dye_the_world", "sofa", "tall_stool");
addColored("dye_the_world", "sofa", "curtain");
addColored("dye_the_world", "sofa", "lamp");
addColored("dye_the_world", "pet_bed", "domesticationinnovation", append);

addColored("dye_the_world", "canvas_sign", "farmersdelight");
addColored("dye_the_world", "hanging_canvas_sign", "farmersdelight");

addColored("snowyspirit", "glow_lights", undefined, append);
addColored("snowyspirit", "gumdrop", undefined, append);

addColored("dye_the_world", "radon_lamp", "alexscaves", append);

addColored("elevatorid", "elevator", undefined, append);

for (const type of ["concrete", "terracotta", "glazed_terracotta"]) {
  addColored("botanypots", `${type}_botany_pot`);
  addColored("botanypots", `${type}_hopper_botany_pot`);
}

const name = "dye-depot-tabs.zip";
const output = resolve(".", name);
const merger = createDefaultMergers({
  silent: true,
  output,
  packFormat,
  title: "adds a creative tab for dye depot's items",
});

const acceptor = merger.createAcceptor();
acceptor("pack.png", readFileSync("icon.png"));
await loader.emit(acceptor);
await merger.finalize();

copyFileSync(output, resolve("..", "run", "resourcepacks", name));

logger.info(`generated zip file at ${output}`);
