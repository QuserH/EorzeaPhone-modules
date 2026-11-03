# EorzeaPhone Modules

随身神典石（开源版）的功能模块仓库。只有经过审核的模块才会合并进这里。

## 提交要求

每个模块必须同时提交：

1. **源码**：放在 `modules/<id>/` 下，能按 README 复现 dex；
2. **包**：`packages/<id>-<版本>.zip`（manifest.json + classes.dex）；
3. **索引**：在 `index.json` 增加条目（id / 名称 / 版本 / sha256 / 包路径 / source）；
4. 说明用途与数据来源；审核通过后合并发布。

模块只能通过 `ModuleHost` 查询物品库；角色、聊天、石之家登录信息不向模块开放。
