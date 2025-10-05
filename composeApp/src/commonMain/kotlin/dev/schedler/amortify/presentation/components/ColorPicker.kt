package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColorPickerInput(
    modifier: Modifier = Modifier,
    initialColor: Color?,
    availableColors: List<Color>,
    onColorSelected: (Color?) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var colorInput by remember {
        mutableStateOf(
            initialColor?.value?.toHexString()?.substring(2, 8).orEmpty().uppercase()
        )
    }
    val color by derivedStateOf {
        runCatching { Color(colorInput.padEnd(6, '0').toLong(16) or 0xFF000000) }.getOrNull()
    }

    LaunchedEffect(color) { onColorSelected(color) }

    OutlinedTextField(
        modifier = modifier,
        label = { Text("Color") },
        value = colorInput,
        isError = color == null && colorInput.isNotEmpty(),
        onValueChange = { colorInput = it.takeLast(6).uppercase() },
        prefix = { Text("#") },
        leadingIcon = {
            val currentColor = color
            when {
                colorInput.isEmpty() -> IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.List,
                        contentDescription = "No color selected"
                    )
                }

                currentColor != null -> Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(currentColor, CircleShape)
                        .clickable { showDialog = true }
                )

                else -> IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Invalid color",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Pick a color") },
            text = {
                ColorGrid(
                    colors = availableColors,
                    selectedColor = color,
                    onColorClick = {
                        colorInput = it.value.toHexString().substring(2, 8).uppercase()
                        showDialog = false
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ColorGrid(
    colors: List<Color>,
    selectedColor: Color?,
    onColorClick: (Color) -> Unit
) {
    val allColors = remember(colors, selectedColor) {
        buildSet {
            addAll(colors)
            if (selectedColor != null) add(selectedColor)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        allColors.chunked(5).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, CircleShape)
                            .clickable { onColorClick(color) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (color == selectedColor) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewColorPickerInput() {
    val demoColors = listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF64B5F6),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFFFD54F),
        Color(0xFFA1887F),
        Color(0xFF90A4AE),
        Color(0xFFFF8A65),
    )

    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ColorPickerInput(
                    initialColor = demoColors.first(),
                    availableColors = demoColors,
                    onColorSelected = {}
                )
                ColorPickerInput(
                    initialColor = null,
                    availableColors = demoColors,
                    onColorSelected = {}
                )
            }
        }
    }
}
