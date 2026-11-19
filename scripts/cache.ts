import { join, resolve } from "node:path";

export type CacheTarget = {
    module: string;
    path: string;
};

export type Cache = {
    file(target: CacheTarget): string;
    outdated(target: CacheTarget, ...inputs: string[]): Promise<boolean>;
    save(): Promise<void>;
};

const CACHE_FILE = Bun.file(join(import.meta.dir, ".cache"));

async function createCache(): Promise<Cache> {
    const previous = new Map<string, string>();
    const next = new Map<string, string>();

    if (await CACHE_FILE.exists()) {
        const raw = await CACHE_FILE.text();
        raw.split("\n")
            .map((it) => it.trim())
            .map((it) => it.split(" "))
            .forEach(([key, hash]) => {
                previous.set(key!, hash!);
            });
    }

    console.info(`loaded previous cache with ${previous.size} keys`);

    const key = ({ module, path }: CacheTarget) => `${module}:${path}`;

    return {
        file: ({ module, path }) => {
            return resolve(
                import.meta.dir,
                "..",
                "modules",
                module,
                "src/dyed/resources/assets",
                path,
            );
        },
        outdated: async (target, ...inputs) => {
            const files = inputs.map((it) => Bun.file(it));
            const hashes = await Promise.all(
                files.map(async (it) => Bun.hash(await it.arrayBuffer())),
            );
            const hash = hashes.join(":");
            next.set(key(target), hash);
            return previous.get(key(target)) !== hash;
        },
        save: async () => {
            await CACHE_FILE.write(
                [...next.entries()].map((it) => it.join(" ")).join("\n"),
            );
        },
    };
}

export function joinCache(
    { path, ...rest }: CacheTarget,
    ...paths: string[]
): CacheTarget {
    return {
        path: join(path, ...paths),
        ...rest,
    };
}

export const CACHE = await createCache();
