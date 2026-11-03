package com.quserh.eorzeaphone.module.sample

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quserh.eorzeaphone.module.api.ModuleEntry
import com.quserh.eorzeaphone.module.api.ModuleHost
import com.quserh.eorzeaphone.module.api.ModuleItem

class SampleEntry : ModuleEntry {
    override val name = "物品搜索"
    override val version = "1.0.2"
    override val description = "示例模块：查询本地物品库（Compose 界面）"

    override fun createView(context: Context, host: ModuleHost): View {
        val view = ComposeView(context)
        view.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        view.setContent { SampleContent(host) }
        return view
    }
}

@Composable
private fun SampleContent(host: ModuleHost) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<ModuleItem>>(emptyList()) }
    var searched by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("物品搜索", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Text("示例模块 · 数据来自宿主物品库", fontSize = 12.sp, color = Color(0xFF777777))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("物品名") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        )
        Button(
            onClick = {
                results = host.searchItems(query, 0, 50)
                searched = true
            },
            modifier = Modifier.padding(top = 8.dp),
        ) { Text("搜索") }
        if (searched && results.isEmpty()) {
            Text("没有匹配的物品。", fontSize = 13.sp, modifier = Modifier.padding(top = 10.dp))
        }
        LazyColumn(Modifier.fillMaxWidth().padding(top = 10.dp)) {
            items(results) { item ->
                Text(
                    "${item.name}  #${item.id}  · 品级 ${item.itemLevel}",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 3.dp),
                )
            }
        }
    }
}
