package dev.schedler.amortify.presentation.cardlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.presentation.components.ColorPickerInput
import dev.schedler.amortify.presentation.components.DateRangePicker
import dev.schedler.amortify.presentation.components.MoneyInput
import dev.schedler.amortify.presentation.util.now
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun CardForm(
    modifier: Modifier = Modifier,
    model: CardModel?,
    onCancel: () -> Unit,
    onSave: (CardModel) -> Unit
) {
    var name by remember { mutableStateOf(model?.name.orEmpty()) }
    var price by remember { mutableStateOf(model?.price) }
    var validityPeriod by remember {
        mutableStateOf(model?.let { it.start to it.end } ?: LocalDate.now().let { it to it })
    }

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
                enabled = name.isNotBlank() && price != null,
                onClick = {
                    if (name.isNotBlank() && price != null) {
                        // TODO
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
            initial = model?.price,
            onValueChange = { price = it },
            label = { Text("Price") },
        )

        ColorPickerInput(
            modifier = Modifier.fillMaxWidth(),
            initialColor = model?.baseColor,
            availableColors = emptyList(),
            onColorSelected = {},
        )

        DateRangePicker(
            modifier = Modifier.fillMaxWidth(),
            initialStart = validityPeriod.first,
            initialEnd = validityPeriod.second,
            label = {
                Text(
                    "Validity",
                    style = MaterialTheme.typography.labelLarge
                )
            },
            onChange = {}
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
