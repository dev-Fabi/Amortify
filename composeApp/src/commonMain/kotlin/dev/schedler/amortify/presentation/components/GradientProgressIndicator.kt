package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    colors: List<Color>,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    gapSize: Dp = ProgressIndicatorDefaults.LinearIndicatorTrackGapSize,
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val indicatorColor by remember(clampedProgress) {
        require(colors.size >= 2) { "At least two colors are required for gradient progress indicator." }
        derivedStateOf {
            val startIndex = lerp(0, colors.lastIndex - 1, clampedProgress)
            val startColor = colors[startIndex]
            val endColor = colors[startIndex + 1]
            lerp(
                startColor,
                endColor,
                (clampedProgress - (startIndex.toFloat() / (colors.size - 1))) * (colors.size - 1)
            )
        }
    }

    LinearProgressIndicator(
        progress = { clampedProgress },
        modifier = modifier,
        color = indicatorColor,
        trackColor = trackColor,
        strokeCap = strokeCap,
        gapSize = gapSize,
    )
}

@Preview(showBackground = true)
@Composable
fun GradientProgressIndicatorPreview() {
    val colors = listOf(
        Color(0xFFD32F2F), // amber
        Color(0xFFFF9800), // orange
        Color(0xFF4DB6AC)  // teal
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        GradientProgressIndicator(progress = 0f, colors = colors)
        GradientProgressIndicator(progress = 0.1f, colors = colors)
        GradientProgressIndicator(progress = 0.3f, colors = colors)
        GradientProgressIndicator(progress = 0.4f, colors = colors)
        GradientProgressIndicator(progress = 0.6f, colors = colors)
        GradientProgressIndicator(progress = 0.7f, colors = colors)
        GradientProgressIndicator(progress = 0.9f, colors = colors)
        GradientProgressIndicator(progress = 1f, colors = colors)
    }
}