package com.quserh.eorzeaphone.module.api

import android.content.Context
import android.view.View

/**
 * 模块可用的宿主能力。只包含物品库只读查询；
 * 角色、聊天、石之家登录态与文件系统一律不在这里出现。
 */
interface ModuleHost {
    fun searchItems(query: String, categoryId: Int = 0, limit: Int = 50): List<ModuleItem>
    fun itemById(id: Int): ModuleItem?
}

data class ModuleItem(
    val id: Int,
    val name: String,
    val kindId: Int,
    val categoryId: Int,
    val iconId: Int,
    val itemLevel: Int,
    val npcPrice: Int,
)

/**
 * 模块入口。UI 用系统 View（android.widget.*）自绘：
 * 这些类不会被 R8 改名，跨 dex 加载比 Compose 稳定。
 */
interface ModuleEntry {
    val name: String
    val version: String
    val description: String

    fun createView(context: Context, host: ModuleHost): View
}
