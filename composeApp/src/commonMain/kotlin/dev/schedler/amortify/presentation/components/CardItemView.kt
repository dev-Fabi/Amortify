package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.UsageTemplateModel
import dev.schedler.amortify.presentation.util.DateFormat
import dev.schedler.amortify.presentation.util.PreviewData
import dev.schedler.amortify.presentation.util.darken
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CardItemView(
    card: CardModel,
    showPercentage: Boolean,
    onEdit: () -> Unit,
    onClick: (() -> Unit)?,
    onAddUsage: ((UsageTemplateModel?) -> Unit)?
) {
    val shape = RoundedCornerShape(16.dp)
    val cardModifier = if (onClick != null) {
        Modifier.clip(shape).clickable(onClick = onClick)
    } else {
        Modifier
    }

    Card(
        modifier = cardModifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(card.baseColor.darken(0.85f), card.baseColor)
                ),
                shape = shape,
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Card", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "€ ${card.price}",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = DateFormat.fromTo(card.start, card.end),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            val progressRowModifier = if (onAddUsage == null) {
                Modifier.padding(top = 12.dp)
            } else {
                Modifier
            }
            Row(
                modifier = progressRowModifier.fillMaxWidth(),
                verticalAlignment = if (showPercentage) Alignment.Bottom else Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val indicatorColor by derivedStateOf {
                        val clampedProgress = card.progress.coerceIn(0f, 1f)
                        lerp(Color.Red, Color.Green, clampedProgress)
                    }
                    GradientProgressIndicator(
                        progress = card.progress,
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth(),
                        trackColor = Color.White.copy(alpha = 0.3f),
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                        colors = listOf(
                            // Use a gradient that is also distinguishable for colorblind users
                            Color(0xFFD32F2F), // amber
                            Color(0xFFFF9800), // orange
                            Color(0xFF4DB6AC)  // teal
                        )
                    )

                    if (showPercentage) {
                        Text(
                            text = "${(card.progress * 100).toInt()}% (€ ${card.used})",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                if (onAddUsage != null) {
                    Spacer(Modifier.width(16.dp))
                    AddUsageButton(
                        templates = card.usageTemplates,
                        onAddUsage = onAddUsage
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardItemView() {
    CardItemView(
        card = PreviewData.gymCard,
        showPercentage = false,
        onEdit = {},
        onClick = {},
        onAddUsage = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCardItemView_WithoutActions() {
    CardItemView(
        card = PreviewData.gymCard,
        showPercentage = true,
        onEdit = {},
        onClick = null,
        onAddUsage = null
    )
}