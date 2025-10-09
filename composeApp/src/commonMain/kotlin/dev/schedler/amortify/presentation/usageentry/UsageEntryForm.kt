package dev.schedler.amortify.presentation.usageentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.presentation.components.DateTimePicker
import dev.schedler.amortify.presentation.components.MoneyInput
import dev.schedler.amortify.presentation.components.rememberDateTimePickerState
import dev.schedler.amortify.presentation.components.rememberMoneyInputState
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
    val dateTime = rememberDateTimePickerState(model?.dateTime ?: Clock.System.now())
    val price = rememberMoneyInputState(model?.price)
    val descriptionState = rememberTextFieldState(model?.description.orEmpty())

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
                enabled = descriptionState.text.isNotBlank() && price.isSet() && dateTime.isSet(),
                onClick = {
                    val money = price.money
                    val instant = dateTime.instant
                    if (descriptionState.text.isNotBlank() && money != null && instant != null) {
                        onSave(
                            UsageEntryModel(
                                dateTime = instant,
                                description = descriptionState.text.toString(),
                                price = money
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
            state = descriptionState,
            label = { Text("Description") },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        MoneyInput(
            modifier = Modifier.fillMaxWidth(),
            state = price,
            label = { Text("Price") },
        )

        DateTimePicker(state = dateTime)
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
