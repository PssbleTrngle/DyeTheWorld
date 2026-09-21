import { exists, mkdir } from "node:fs/promises";
import { dirname } from "node:path";

export async function ensureDir(path: string) {
    if (!(await exists(path))) {
        await mkdir(path, { recursive: true });
    }
}

export async function ensureParentDir(path: string) {
    await ensureDir(dirname(path));
}

export function notNull<T>(value: T | null | undefined): value is T {
    return value !== null && value !== undefined;
}
