# Traincraft 1.12.2 — Unofficial Port

An unofficial port of [Traincraft](https://github.com/Traincraft/Traincraft) to **Minecraft 1.12.2**
(Forge / Cleanroom). **It's playable:** trains place, ride, drive, couple and haul cargo, tracks
and signals render, and it runs in a large 1.12.2 modpack. As with any mod, back up your world
before adding it.

## Download

**Latest:** [v4.4.1-1.12.2](https://github.com/crunkazcanbe/traincraft-1.12.2-port/releases/latest)
— see [CHANGELOG.md](CHANGELOG.md) for what changed.

Requires **Minecraft 1.12.2** with Forge or Cleanroom. Please report anything broken via Issues
(a screenshot plus the crash report or `latest.log` helps a lot).

## Driving a locomotive

1. Get on, press **R** to open the train screen, and press **Start Engine**.
2. Diesel locos take fuel in the **first slot** (the Diesel Canister, `tc:diesel`).
3. Tap **Y** to raise the throttle, **then** hold **W**. W alone does nothing. **S** brakes/reverses.

Other keys: **H** horn, **X** throttle down, **C** idle. To couple, hold a **Stake** and
right-click one car, then the other.

## What this is

The original Traincraft targets older Minecraft versions. This port gets it running on
**1.12.2** so it can live alongside a modern 1.12.2 modpack.

This port is a hands-on project done **with the help of Claude (Anthropic's AI)**, worked through
one bug at a time. The code reflects that: lots of targeted fixes and comments explaining *why*
things changed.

## Known issues

- **Train weight has no effect yet.** A small shunter pulls a long train at full speed.
- **Couplings may not survive a world reload.** Only the "is linked" flag is saved, not which
  car is on each end.
- **Ride height on slopes** is a little low, and a train that stops on a slope may not restart.
- Train hitboxes are small and sit at one end, so trains can be **hard to click or break**.
- Not ported yet from 1.7.10: 40 ft / 53 ft shipping containers (they show a "work in progress"
  tooltip), destination signs, and ATO/MTC automation.

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
