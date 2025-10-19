package dev.schedler.amortify.presentation.cardlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.ISimpleCard
import dev.schedler.amortify.domain.model.SimpleCardModel
import dev.schedler.amortify.presentation.components.ColorPickerInput
import dev.schedler.amortify.presentation.components.DateRangePicker
import dev.schedler.amortify.presentation.components.MoneyInput
import dev.schedler.amortify.presentation.components.rememberColorInputState
import dev.schedler.amortify.presentation.components.rememberDateRangePickerState
import dev.schedler.amortify.presentation.components.rememberMoneyInputState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CardForm(
    modifier: Modifier = Modifier,
    model: ISimpleCard?,
    onCancel: () -> Unit,
    onSave: (SimpleCardModel) -> Unit
) {
    var name by remember { mutableStateOf(model?.name.orEmpty()) }
    val price = rememberMoneyInputState(model?.price)
    val baseColor = rememberColorInputState(model?.baseColor)
    val validityPeriod = rememberDateRangePickerState(
        initialStart = model?.start,
        initialEnd = model?.end
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onCancel) {
                Icon(Icons.Default.Close, contentDescription = "Cancel")
            }

            Button(
                enabled = name.isNotBlank() && price.isSet() && baseColor.isSet() && validityPeriod.isValid(),
                onClick = {
                    val money = price.money
                    val color = baseColor.color
                    val start = validityPeriod.range.first
                    val end = validityPeriod.range.second
                    if (name.isNotBlank() && money != null && color != null && start != null && end != null) {
                        onSave(
                            SimpleCardModel(
                                id = model?.id,
                                name = name,
                                price = money,
                                baseColor = color,
                                start = start,
                                end = end,
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true
        )

        MoneyInput(
            modifier = Modifier.fillMaxWidth(),
            state = price,
            label = { Text("Price") },
        )

        ColorPickerInput(
            modifier = Modifier.fillMaxWidth(),
            state = baseColor,
            availableColors = listOf(
                // Warm / Light
                Color(0xFFFFF5E1), // SoftSand
                Color(0xFFFFF3CF), // WarmCream
                Color(0xFFFFEEDB), // PaleApricot
                Color(0xFFFFEAD5), // SoftPeach
                Color(0xFFFFD699), // DeepWarmBeige

                // Cool / Gray
                Color(0xFFF2F2F2), // Mist
                Color(0xFFE0E0E0), // CoolGray
                Color(0xFFB0BEC5), // MistyGray
                Color(0xFFD9BFA0), // DarkSand
                Color(0xFFA1887F), // Cocoa

                // Teal / Cyan / Green
                Color(0xFFC8EAE5), // SoftMint
                Color(0xFFBEEAE6), // PaleCyan
                Color(0xFFD0F0F0), // LightAqua
                Color(0xFF4DB6AC), // SlateTeal
                Color(0xFF388E8E), // TealGray
                Color(0xFF2E7D7D), // OliveTeal
                Color(0xFF009688), // DeepCyan
            ),
        )

        DateRangePicker(
            modifier = Modifier.fillMaxWidth(),
            state = validityPeriod,
            label = {
                Text(
                    "Validity",
                    style = MaterialTheme.typography.labelLarge
                )
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewCardForm() {
    CardForm(
        model = null,
        onCancel = {},
        onSave = {}
    )
}
