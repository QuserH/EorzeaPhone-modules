package com.quserh.eorzeaphone.module.sample

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.quserh.eorzeaphone.module.api.ModuleEntry
import com.quserh.eorzeaphone.module.api.ModuleHost

class SampleEntry : ModuleEntry {
    override val name = "物品搜索"
    override val version = "1.0.1"
    override val description = "示例模块：查询本地物品库"

    override fun createView(context: Context, host: ModuleHost): View {
        val density = context.resources.displayMetrics.density
        fun dp(value: Int) = (value * density).toInt()
        val match = ViewGroup.LayoutParams.MATCH_PARENT
        val wrap = ViewGroup.LayoutParams.WRAP_CONTENT

        val root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
            setBackgroundColor(Color.WHITE)
        }

        root.addView(TextView(context).apply {
            text = "物品搜索"
            textSize = 22f
            setTypeface(Typeface.DEFAULT_BOLD)
        })

        val input = EditText(context).apply {
            hint = "输入物品名，例如：药"
            setSingleLine(true)
        }
        root.addView(input, LinearLayout.LayoutParams(match, wrap).apply { topMargin = dp(12) })

        val result = TextView(context).apply {
            textSize = 13f
            setTypeface(Typeface.MONOSPACE)
            setTextColor(Color.rgb(40, 44, 52))
        }
        val scroll = ScrollView(context).apply { addView(result) }

        root.addView(Button(context).apply {
            text = "搜索"
            setOnClickListener {
                val items = host.searchItems(input.text.toString(), 0, 50)
                result.text = if (items.isEmpty()) {
                    "没有匹配的物品。"
                } else {
                    items.joinToString("\n") { "${it.name}  #${it.id}  · 品级 ${it.itemLevel}" }
                }
            }
        }, LinearLayout.LayoutParams(match, wrap).apply { topMargin = dp(8) })

        root.addView(scroll, LinearLayout.LayoutParams(match, 0, 1f).apply { topMargin = dp(8) })
        return root
    }
}
