package dev.schedler.amortify

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.schedler.amortify.data.local.db.getDatabaseBuilder

fun main() = application {
    val dbBuilder = remember { getDatabaseBuilder() }
    val windowState = rememberWindowState(width = 450.dp, height = 900.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Amortify",
    ) {
        App(dbBuilder)
    }
}