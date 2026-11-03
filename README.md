# EorzeaPhone Modules

随身神典石（开源版）的功能模块仓库。只有经过审核的模块才会合并进这里，App 的「功能库 → 模块」从本仓库读取索引。

## 结构

```
index.json                 # 模块索引（id / 名称 / 版本 / sha256 / 包路径）
packages/<id>-<版本>.zip    # 模块包：manifest.json + classes.dex
```

## 提交模块

1. 模块实现 `com.quserh.eorzeaphone.module.api.ModuleEntry`，UI 用系统 View 自绘。
2. 打包 zip（manifest.json + classes.dex），计算 SHA-256。
3. 提 PR 更新 index.json 并附上包文件，说明用途与数据来源。
4. 审核通过后合并发布。

模块只能通过 `ModuleHost` 查询物品库；角色、聊天、石之家登录信息不向模块开放。
