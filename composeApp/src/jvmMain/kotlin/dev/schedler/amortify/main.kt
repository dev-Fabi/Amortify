package dev.schedler.amortify

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.schedler.amortify.data.local.db.getDatabaseBuilder

fun main() = application {
    val dbBuilder = remember { getDatabaseBuilder() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Amortify",
    ) {
        App(dbBuilder)
    }
}