package dev.schedler.amortify

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import dev.schedler.amortify.data.local.db.getDatabaseBuilder

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController {
    val dbBuilder = remember { getDatabaseBuilder() }
    App(dbBuilder)
}