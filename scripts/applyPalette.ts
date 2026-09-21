import { exists, glob } from "node:fs/promises";
import { parse, resolve } from "node:path";
import { aseprite } from "./aseprite";
import { DYE_DEPOT_COLORS } from "./colors";
import { ensureParentDir, notNull } from "./util";

export type Namer = (name: string, color: string) => string;

export type NamingStrategy = "prefix" | "suffix";

function createNamer(strategy: NamingStrategy | Namer = "prefix"): Namer {
    if (typeof strategy !== "string") return strategy;

    if (strategy === "prefix") {
        return (n, c) => `${c}_${n}`;
    }
    if (strategy === "suffix") {
        return (n, c) => `${n}_${c}`;
    }

    throw new Error(`unknown naming strategy '${strategy}'`);
}

export type Dyed = {
    palette: string;
    from: string;
    to: string;
    include?: string[];
    exclude?: string[];
    naming?: NamingStrategy | Namer;
};

export async function createDyedTextures({
    palette,
    from,
    to,
    exclude = [],
    include = [],
    naming = "prefix",
}: Dyed) {
    if (include.length === 0) {
        include.push("**/*");
    }

    const namer = createNamer(naming);

    const paletteFiles = await Promise.all(
        DYE_DEPOT_COLORS.map(async (color) => {
            const paletteFile = resolve(
                import.meta.dir,
                "palettes",
                palette,
                color + ".ase",
            );

            if (!(await exists(paletteFile))) {
                console.error(
                    `missing palette of type ${palette} for ${color}`,
                );
                return null;
            }

            return { color, paletteFile };
        }),
    ).then((it) => it.filter(notNull));

    for await (const match of glob(
        include.map((it) => `${it}.png`),
        {
            cwd: from,
            exclude,
        },
    )) {
        for (const { color, paletteFile } of paletteFiles) {
            const fromFile = resolve(from, match);

            const { name, ext, dir } = parse(match);

            const renamed = namer(name, color);

            const toFile = resolve(to, dir, renamed + ext);
            await ensureParentDir(toFile);
            await aseprite("applyPalette", {
                from: fromFile,
                to: toFile,
                palette: paletteFile,
            });

            if (!(await exists(toFile))) {
                throw new Error(
                    `something went wrong generating ${match} for color ${color} (${renamed})`,
                );
            }

            console.log(`  saved to ${toFile}`);
        }
    }
}
