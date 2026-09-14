import os, json, re

base = "/src/main/resources/assets/tc"

# ============================================================
# 1. FIX oreTC CASE SENSITIVITY ISSUES
# ============================================================
bs_path = os.path.join(base, "blockstates/oretc.json")
with open(bs_path) as f:
    bs = json.load(f)
for key, val in bs.get("variants", {}).items():
    if "model" in val:
        model = val["model"]
        if model.startswith("tc:oreTC_"):
            val["model"] = "tc:" + model.replace("tc:oreTC_", "oretc_").lower()
with open(bs_path, "w") as f:
    json.dump(bs, f, indent=2)
    f.write("\n")
print("Fixed blockstates/oretc.json")

for fname in ["oretc_copperore", "oretc_oilsands", "oretc_petroleum", "oretc_ballast"]:
    fpath = os.path.join(base, f"models/item/{fname}.json")
    with open(fpath) as f:
        data = json.load(f)
    if "parent" in data and "oreTC_" in data["parent"]:
        data["parent"] = "tc:block/" + data["parent"].replace("tc:block/oreTC_", "oretc_").lower()
        with open(fpath, "w") as f:
            json.dump(data, f, indent=2)
            f.write("\n")
        print(f"Fixed item model {fname}.json")

# ============================================================
# 2. CREATE MISSING BLOCKSTATE JSONS
# ============================================================
signal_bs = {"variants": {"normal": {"model": "tc:signal"}}}
with open(os.path.join(base, "blockstates/signal.json"), "w") as f:
    json.dump(signal_bs, f, indent=2)
    f.write("\n")
print("Created blockstates/signal.json")

signal_model = {"parent": "builtin/entity", "textures": {}}
with open(os.path.join(base, "models/block/signal.json"), "w") as f:
    json.dump(signal_model, f, indent=2)
    f.write("\n")
print("Created models/block/signal.json")

diesel_bs = {"forge_marker": 1, "defaults": {"model": "forge:fluid", "custom": {"fluid": "Diesel"}}, "variants": {"normal": [{}], "inventory": [{"transform": "forge:default-item"}]}}
with open(os.path.join(base, "blockstates/diesel.json"), "w") as f:
    json.dump(diesel_bs, f, indent=2)
    f.write("\n")
print("Created blockstates/diesel.json")

refinedfuel_bs = {"forge_marker": 1, "defaults": {"model": "forge:fluid", "custom": {"fluid": "RefinedFuel"}}, "variants": {"normal": [{}], "inventory": [{"transform": "forge:default-item"}]}}
with open(os.path.join(base, "blockstates/refinedfuel.json"), "w") as f:
    json.dump(refinedfuel_bs, f, indent=2)
    f.write("\n")
print("Created blockstates/refinedfuel.json")

# ============================================================
# 3. CREATE MISSING ITEM MODEL JSONs
# ============================================================
tex_base = os.path.join(base, "textures/items")
model_dir = os.path.join(base, "models/item")

tex_map = {}
for subdir in ["", "trains/", "parts/", "armour/", "tracks/"]:
    full_dir = os.path.join(tex_base, subdir)
    if not os.path.isdir(full_dir):
        continue
    for fname in os.listdir(full_dir):
        if fname.endswith(".png") and not fname.endswith(".png.mcmeta"):
            basename = fname[:-4]
            if subdir:
                tex_map[basename] = subdir + basename
            else:
                tex_map[basename] = basename

print(f"Total textures found: {len(tex_map)}")

created_count = 0
for basename, tex_path in sorted(tex_map.items()):
    model_file = os.path.join(model_dir, basename + ".json")
    if os.path.exists(model_file):
        continue
    actual_tex = os.path.join(tex_base, tex_path + ".png")
    if not os.path.exists(actual_tex):
        continue
    model = {"parent": "item/generated", "textures": {"layer0": "tc:items/" + tex_path}}
    with open(model_file, "w") as f:
        json.dump(model, f, indent=2)
        f.write("\n")
    created_count += 1

print(f"Created {created_count} missing item model JSONs")

print("All done!")
