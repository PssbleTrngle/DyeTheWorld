import { $ } from "bun";

export async function aseprite(script: string, params: Record<string, string>) {
    const args = Object.entries(params)
        .map(
            ([key, value]) =>
                `-script-param ${key}=${value.replaceAll("\\", "/")}`,
        )
        .join(" ");
    await $`aseprite -b ${{ raw: args }} -script ${script}.lua`;
}
