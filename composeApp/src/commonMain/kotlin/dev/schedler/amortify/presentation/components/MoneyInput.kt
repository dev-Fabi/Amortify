package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import dev.schedler.amortify.domain.model.Money
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MoneyInput(
    modifier: Modifier = Modifier,
    initial: Money? = null,
    allowNegative: Boolean = false,
    currencySymbol: @Composable (() -> Unit) = {
        Icon(Icons.Default.EuroSymbol, contentDescription = "EUR")
    },
    decimalSeparator: Char = '.',
    onValueChange: (Money?) -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    var state by remember { mutableStateOf(initial?.toString(decimalSeparator)) }
    val moneyRegex = remember(allowNegative) {
        if (allowNegative) {
            Regex("""^(- ?)?(\d+([.,]\d{0,2})?)?$""")
        } else {
            Regex("""^(\d+([.,]\d{0,2})?)?$""")
        }
    }

    LaunchedEffect(state) {
        onValueChange(state?.let { Money.fromString(it) })
    }

    OutlinedTextField(
        modifier = modifier,
        label = label,
        placeholder = { Text(text = "0${decimalSeparator}00") },
        singleLine = true,
        value = state.orEmpty(),
        onValueChange = { newValue ->
            if (!newValue.matches(moneyRegex)) return@OutlinedTextField
            state = newValue.replace(Regex("[,.]"), decimalSeparator.toString())
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
        leadingIcon = currencySymbol,
        trailingIcon = {
            IconButton(onClick = { state = null }) {
                Icon(Icons.Default.Clear, contentDescription = "clear")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewMoneyInput() {
    MoneyInput(
        initial = Money(199),
        onValueChange = { },
        label = { Text("Price") }
    )
}
