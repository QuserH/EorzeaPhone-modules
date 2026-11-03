#!/usr/bin/env python3
"""把构建产出的 dex 打成模块包，并更新 index.json。由 CI 调用。"""
import hashlib
import json
import os
import sys
import zipfile

out_root = sys.argv[1] if len(sys.argv) > 1 else "out"
os.makedirs("packages", exist_ok=True)
entries = []

for module_dir in sorted(os.listdir("modules")):
    manifest_path = os.path.join("modules", module_dir, "module.json")
    if not os.path.isfile(manifest_path):
        continue
    meta = json.load(open(manifest_path, encoding="utf-8"))
    dex = os.path.join(out_root, module_dir, "classes.dex")
    if not os.path.isfile(dex):
        raise SystemExit(f"missing dex for {module_dir}")
    import tempfile

    with tempfile.NamedTemporaryFile(suffix=".zip", delete=False) as tmp:
        tmp_path = tmp.name
    with zipfile.ZipFile(tmp_path, "w", zipfile.ZIP_DEFLATED) as z:
        z.write(dex, "classes.dex")
        z.writestr("manifest.json", json.dumps(meta, ensure_ascii=False, indent=2))
    sha = hashlib.sha256(open(tmp_path, "rb").read()).hexdigest()
    # 文件名带内容哈希：CDN 缓存与索引永远不会指向不同内容。
    pkg_name = f"{meta['id']}-{meta['version']}-{sha[:12]}.zip"
    pkg_path = os.path.join("packages", pkg_name)
    os.replace(tmp_path, pkg_path)
    entries.append({
        "id": meta["id"],
        "name": meta["name"],
        "version": meta["version"],
        "description": meta.get("description", ""),
        "sha256": sha,
        "path": f"packages/{pkg_name}",
        "source": f"modules/{module_dir}",
    })

with open("index.json", "w", encoding="utf-8", newline="\n") as f:
    f.write(json.dumps({"modules": entries}, ensure_ascii=False, indent=2) + "\n")
print("published", len(entries), "module(s)")
