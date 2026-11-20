import { join } from "node:path";
import { createDyedTextures } from "./applyPalette";
import { CACHE, type CacheTarget } from "./cache";

const { FORKS_DIR } = process.env;
if (!FORKS_DIR) throw new Error(`env variable 'FORKS_DIR' not set`);

const createTextures = join(
    FORKS_DIR,
    "Create/src/main/resources/assets/create/textures",
);

const artsAndCraftsTextures = join(
    FORKS_DIR,
    "Arts-and-Crafts/common/src/main/resources/assets/arts_and_crafts/textures",
);

function textureFolder(mod: string, ...type: string[]): CacheTarget {
    return {
        module: mod,
        path: join(mod, "textures", ...type),
    };
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

async function createDyedChalkMarks(from: string) {
    await createDyedTextures({
        from: join(artsAndCraftsTextures, "block"),
        to: textureFolder("arts_and_crafts", "block"),
        palette: "chalk",
        naming: (name, color) => name.replace(`${from}_`, `${color}_`),
        include: [
            `${from}_circle`,
            `${from}_arch_*`,
            `${from}_corner_*`,
            `${from}_block_corner_*`,
            `${from}_plus`,
            `${from}_dot`,
            `${from}_*_line_*`,
            `${from}_line_*`,
            `${from}_cross_*`,
            `${from}_t_cross_*`,
            `${from}_x`,
            `${from}_triangle_*`,
            `${from}_square`,
        ],
    });
}

async function createDyedChalkParticles(from: string) {
    await createDyedTextures({
        from: join(artsAndCraftsTextures, "particle", "chalk_draw", from),
        to: textureFolder("arts_and_crafts", "particle", "chalk_draw"),
        palette: "chalk",
        naming: (name, color) => join(color, name.replace(/(\d)/, "_$1")),
    });
}

// if (await exists(generatedAssets)) {
//     await rm(generatedAssets, { recursive: true });
// }

await createDyedPipes("dyed_smart_pipe", ["smart_pipe*"]);
await createDyedPipes("dyed_fluid_tank", ["fluid_tank*", "boiler_gauge"]);
await createDyedPipes("dyed_pipes", ["pipes*"]);
await createDyedPipes("dyed_valve", ["*valve*"]);
await createDyedPipes("dyed_pump", ["pump"]);
await createDyedPipes("dyed_steam_engine", ["engine"]);

await createDyedChalkMarks("red");
await createDyedChalkParticles("red");

await CACHE.save();
