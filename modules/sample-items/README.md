# 物品搜索（示例模块）

源码公开，便于审核。构建方式：

1. 用 Android SDK 编译 `:module:assembleRelease`（compileOnly 依赖宿主 API，不打包进 dex）。
2. 取出 `module/build/intermediates/aar_main_jar/release/syncReleaseLibJars/classes.jar`。
3. `d8 --release --min-api 24 --lib <sdk>/platforms/android-36/android.jar --output out classes.jar`
4. 打包 `classes.dex` + `manifest.json` 为 zip，计算 sha256 后更新仓库 index.json。

模块只通过 `ModuleHost.searchItems/itemById` 读取物品库，不访问角色、聊天或石之家数据。
