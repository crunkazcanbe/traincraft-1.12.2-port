# Traincraft 1.12.2 — Unofficial Experimental Port

> ⚠️ **EXTREMELY EXPERIMENTAL. MAJOR BUGS. DO NOT USE THIS IN A WORLD YOU CARE ABOUT.** ⚠️
>
> This is a work-in-progress, unofficial port of [Traincraft](https://github.com/Traincraft/Traincraft)
> to **Minecraft 1.12.2** (Forge / Cleanroom). It is nowhere near stable — it can crash, render
> things wrong, and potentially damage saves. **Back up any world first, or better, use a
> throwaway test world.**

## What this is

The original Traincraft targets older Minecraft versions. This is an in-progress effort to get
it running on **1.12.2** so it can live alongside a modern 1.12.2 modpack. A lot already works —
trains place, ride, couple, and haul cargo; most models, tracks, and block entities render — but
plenty is still rough or broken.

This port is a hands-on project done **with the help of Claude (Anthropic's AI)**, worked through
one bug at a time. The code reflects that: lots of targeted fixes and comments explaining *why*
things changed.

## Known issues (the "major bugs" part — non-exhaustive)

- **Bogie locomotives** could render rotated the wrong way on some track directions. A fix
  restoring the original upstream rotation math was just applied and is **under test**.
- **Cargo wagons** can sit partially buried in the ground (vertical-offset issue, not yet fixed).
- Train hitboxes are small and at one end — trains can be **hard to click/break** and may
  **vanish** behind foliage on occasion.
- Some locomotive seat positions may still be slightly off.
- Assorted texture, model, and crash issues remain.

A bug report with a screenshot + the crash report is genuinely helpful.

## Building

Java + RetroFuturaGradle: `./gradlew build` → jar in `build/libs/`. Drop it into a 1.12.2
Forge/Cleanroom `mods/` folder.

## Credits

The original mod is by **the Traincraft team** — originally **Spitfire4466**, then with
**Mrbrutal**, and the 1.7.10 community fork by **EternalBlueFlame** and **NitroxydeX**.
Repo: https://github.com/Traincraft/Traincraft — **all credit for the mod, its assets, and its
models belongs to them.** This 1.12.2 port is an unofficial derivative and is not affiliated
with or endorsed by the original authors.

## Licensing

This port is derived from **[EternalBlueFlame/Traincraft](https://github.com/EternalBlueFlame/Traincraft)**
(the "Traincraft-5" community fork), which is released under the **GNU Lesser General Public License
v2.1**. In keeping with that license, **this port is also licensed under the LGPL v2.1** — see the
[`LICENSE`](LICENSE) file. You are free to use, modify, and redistribute it under those terms, which
in short means: keep the source available, keep it under the LGPL, and **credit the original authors**.

**All credit for Traincraft — the mod, its code, its models, and its textures — belongs to its
original creators, not to this port.** This is only a version port; none of the underlying mod was
invented here. Please keep their names attached to any use of this work (see Credits above).

Bundled third-party dependency licenses (Forge, FML, Railcraft, CoFH/LGPLv3) are also included in the repo.
If you are a Traincraft author and want this handled differently, please open an issue — it will be respected.

### Compile-only jar
Put `buildcraft-8.0.0-deduped.jar` (BuildCraft 8.0.0 for 1.12.2) into `libs/` before building — it is not redistributed here.
