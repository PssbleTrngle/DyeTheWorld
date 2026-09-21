import { exists, rm } from "node:fs/promises";
import { join, resolve } from "node:path";
import { createDyedTextures } from "./applyPalette";

const generatedAssets = resolve(
    import.meta.dir,
    "..",
    "src/dyed/resources/assets",
);

const createTextures =
    "D:/repos/Minecraft/Forks/Create/src/main/resources/assets/create/textures";

function textureFolder(mod: string, ...type: string[]) {
    return resolve(generatedAssets, mod, "textures", ...type);
}

async function createDyedPipes(type: string, include: string[]) {
    await createDyedTextures({
        from: join(createTextures, "block"),
        to: textureFolder("bits_n_bobs", "block", type),
        naming: "suffix",
        palette: "pipe",
        include,
    });
}

if (await exists(generatedAssets)) {
    await rm(generatedAssets, { recursive: true });
}

await createDyedPipes("dyed_smart_pipe", ["smart_pipe*"]);
await createDyedPipes("dyed_fluid_tank", ["fluid_tank*", "boiler_gauge"]);
await createDyedPipes("dyed_pipes", ["pipes*"]);
await createDyedPipes("dyed_valve", ["*valve*"]);
await createDyedPipes("dyed_pump", ["pump"]);
await createDyedPipes("dyed_steam_engine", ["engine"]);
