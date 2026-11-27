import { exists, mkdir } from "node:fs/promises";
import { join, resolve } from "node:path";

const [, , name] = process.argv;
if (!name) throw new Error("module name not given");

async function ensureDir(...path: string[]) {
    const file = join(...path);
    if (await exists(file)) return;
    await mkdir(file, { recursive: true });
    console.info("created", file);
}

const moduleDir = resolve("modules", name);

await ensureDir(
    moduleDir,
    "src/main/kotlin/com/possible_triangle/dye_the_world",
    name,
);

await ensureDir(
    moduleDir,
    "src/main/java/com/possible_triangle/dye_the_world",
    name,
);

await ensureDir(moduleDir, "src/main/resources/assets", name);

await Bun.file(join(moduleDir, "build.gradle.kts")).write(
    "TODO build.gradle.kts",
);
