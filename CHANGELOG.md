# Changelog

All notable changes to this port. Versions match the upstream Traincraft version plus a
port suffix (`-alphaN` for the early test builds).

## v4.4.3-1.12.2

### Added
- **Per-train-type dimension rules** (issue #6): new `dimensions` config section with `SteamDims`, `DieselDims`,
  `ElectricDims` and a `…Whitelist` true/false for each. Default = blacklist (that type won't start in the listed
  dimension IDs); whitelist = that type only works there. Empty lists keep the old behaviour. A locomotive that isn't
  allowed in its dimension keeps its engine off and tells the driver why. See the README for an example.

## v4.4.2-1.12.2

Fixes for the four reported bugs (#1–#4): curve track models, locomotive placement direction, long-straight hitbox,
track-stake double click.

## v4.4.1-1.12.2

First non-alpha release. **Same code as alpha2**: it has been played and tested since then
(driving, fuel, coupling, hauling cargo, tracks, signals), so the "experimental" label is gone.
The README now has a short "Driving a locomotive" guide and an up-to-date known-issues list.

## v4.4.1-1.12.2-alpha2

The port was missing **75 classes** from 1.7.10-CE. No textures were missing — 1.12.2 ships
*more* than 1.7.10 — it was dropped **code**, usually with the assets already sitting in the
jar unused.

### Fixed — track rendering (~53 track types)
- Every **diagonal straight** and every **embedded straight** rendered as *nothing*. New
  `ModelDiagonalStraightTCTrack`, recovered from 1.7.10-CE.
- **45° turns** and **parallel curves** were drawn with the wrong model — a 3x4 45° curve used
  the 3x3 **90°** model, so the rails did not line up with the track.
- **Curved slopes** were drawn as straight slopes.
- Long / very-long straights now tile correctly (a run lays a node every 3 blocks).
- `EMBEDDED_*` track now binds `track_embedded.png`.

### Fixed — six blocks that were never instantiated
`signal`, `kSignal`, `signalSpanish`, `autoSwitchStand`, `overheadWire`, `overheadWireDouble`
were declared but never created, so there were no signals and no catenary in the game at all.
Enabling them exposed and fixed:
- `TilekSignal` / `TilesignalSpanish` were never registered → *"is missing a mapping!"* on
  every world save, losing state.
- `RenderSignal`'s texture binds were commented out, so signals drew with the block atlas
  still bound.
- 10 TESR blockstates drew a stretched cube under the real renderer ("broken aspect ratio").
- `BlockSignal.onBlockPlacedByOld` overrode nothing, so signals never got a facing; and the
  cart-detection box used the old ForgeDirection numbering, shrinking it to 1 block.

### Fixed — coupling persistence
Couplings did not survive a world reload: the cart **id** was saved but the live reference was
not, and nothing rebuilt it. Consists came back looking coupled, pulling nothing, and drifting
together. This also revived the **load physics** — "Carts pulled" / "Mass pulled" no longer
read 0, so weight affects speed, acceleration and braking again.

### Fixed — other
- The **paintbrush** did nothing at all; its tooltip promised a menu that did not exist. New
  colour menu + packets.
- Restored the **second creative tab** for rolling stock (~500 trains were buried among ~1600
  items).
- Diesel locos showed **"Fuel: 0"** with a full tank (the GUI read the solid-fuel counter).
- An unset colour produced filenames like `c62_front_-1.png`; now falls back to Black.
- Per-tick debug tracing moved behind a flag — it was **63%** of the log.
- Session metadata directories are excluded from the built jar.

### Known gaps
Not yet ported from 1.7.10: the destination/sign overlay system, the 40ft/53ft shipping
containers (work-in-progress upstream as well), and recipe-book search. Electric locomotives
need an external RF energy source above or below the track — that is upstream behaviour, not a
port regression.

## v4.4.1-1.12.2-alpha
First public alpha of the port.
