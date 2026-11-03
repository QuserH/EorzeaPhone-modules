# 物品搜索（示例模块）

源码随包提交，审核通过后由 GitHub Actions 自动编译为 dex 并发布。

- UI：系统 View（android.widget.*），跨 dex 加载不受 R8 改名影响。
- 数据：只通过 `ModuleHost.searchItems/itemById` 查询物品库。
