package dev.schedler.amortify.presentation.usageentry

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.presentation.components.DateTimePicker
import dev.schedler.amortify.presentation.components.MoneyInput
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun UsageEntryForm(
    modifier: Modifier = Modifier,
    model: UsageEntryModel?,
    onCancel: () -> Unit,
    onSave: (UsageEntryModel) -> Unit
) {
    var description by remember { mutableStateOf(model?.description.orEmpty()) }
    var dateTime by remember { mutableStateOf(model?.dateTime ?: Clock.System.now()) }
    var price by remember { mutableStateOf(model?.price) }

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
                enabled = description.isNotBlank() && dateTime != null && price != null,
                onClick = {
                    if (description.isNotBlank() && dateTime != null && price != null) {
                        onSave(
                            UsageEntryModel(
                                dateTime = dateTime!!,
                                description = description,
                                price = price!!
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
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = true
        )

        MoneyInput(
            modifier = Modifier.fillMaxWidth(),
            initial = model?.price,
            onValueChange = { price = it },
            label = { Text("Price") },
        )

        DateTimePicker(
            initial = dateTime,
            onChange = { dateTime = it }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewUsageEntryForm() {
    UsageEntryForm(
        model = null,
        onCancel = {},
        onSave = {}
    )
}
