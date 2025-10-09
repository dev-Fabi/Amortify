package dev.schedler.amortify.presentation.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.filterIsInstance

@Composable
fun rememberClickableInteractionSource(onClick: () -> Unit): MutableInteractionSource {
    return remember {
        MutableInteractionSource()
    }.also { source ->
        LaunchedEffect(source) {
            source.interactions
                .filterIsInstance<PressInteraction.Release>()
                .collect { onClick() }
        }
    }
}