package dev.schedler.amortify.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.UsageTemplateModel
import dev.schedler.amortify.presentation.util.PreviewData
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddUsageButton(
    templates: List<UsageTemplateModel>,
    onAddUsage: (UsageTemplateModel?) -> Unit,
) {
    if (templates.isEmpty()) {
        Button(
            onClick = { onAddUsage(null) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Companion.White)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Entry",
                tint = Color.Companion.Black
            )
            Spacer(modifier = Modifier.Companion.width(8.dp))
            Text("Entry", color = Color.Companion.Black)
        }
    } else {
        var showTemplates by remember { mutableStateOf(false) }
        val iconRotation by animateFloatAsState(if (showTemplates) 180f else 0f)
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(
                    onClick = { onAddUsage(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Companion.White)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Entry",
                        tint = Color.Companion.Black,
                        modifier = Modifier.Companion.size(SplitButtonDefaults.LeadingIconSize)
                    )
                    Spacer(modifier = Modifier.Companion.width(8.dp))
                    Text("Entry", color = Color.Companion.Black)
                }
            },
            trailingButton = {
                SplitButtonDefaults.TrailingButton(
                    checked = showTemplates,
                    onCheckedChange = { showTemplates = it },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Companion.White)
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = if (showTemplates) "Hide Templates" else "Show Templates",
                        tint = Color.Companion.Black,
                        modifier = Modifier.Companion.graphicsLayer {
                            rotationZ = iconRotation
                        }
                    )
                }

                DropdownMenu(
                    expanded = showTemplates,
                    onDismissRequest = { showTemplates = false }
                ) {
                    for (template in templates) {
                        DropdownMenuItem(
                            text = { Text("${template.description} (${template.price})") },
                            onClick = {
                                showTemplates = false
                                onAddUsage(template)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewAddUsageButton() {
    Column {
        AddUsageButton(
            templates = PreviewData.usageTemplates,
            onAddUsage = {}
        )
        AddUsageButton(
            templates = emptyList(),
            onAddUsage = {}
        )
    }
}