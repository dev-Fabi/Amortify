package dev.schedler.amortify.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import dev.schedler.amortify.domain.model.Money
import dev.schedler.amortify.util.LocalizationUtils
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.jvm.JvmInline

/**
 * A text field for monetary input, handling formatting and input restrictions.
 * The state contains the raw value in cents (1/100).
 */
@Composable
fun MoneyInput(
    modifier: Modifier = Modifier,
    state: MoneyInputState = rememberMoneyInputState(),
    currencySymbol: @Composable (() -> Unit) = {
        Icon(Icons.Default.EuroSymbol, contentDescription = "EUR")
    },
    decimalSeparator: Char = LocalizationUtils.decimalSeparator,
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
) {

    OutlinedTextField(
        modifier = modifier,
        state = state.backingState,
        inputTransformation = {
            asCharSequence().forEachIndexed { index, char ->
                if (!char.isDigit()) delete(index, index + 1)
            }
            if (length > 3 && charAt(0) == '0') delete(0, 1)
        },
        outputTransformation = {
            when {
                length == 0 -> Unit
                length == 1 -> insert(0, "0${decimalSeparator}0")
                length == 2 -> insert(0, "0${decimalSeparator}")
                length > 2 -> insert(length - 2, decimalSeparator.toString())
            }
        },
        placeholder = { Text(text = "0${decimalSeparator}00") },
        label = label,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        leadingIcon = currencySymbol,
        trailingIcon = {
            IconButton(
                onClick = { state.backingState.clearText() }
            ) {
                Icon(Icons.Default.Clear, contentDescription = "clear")
            }
        }
    )
}

@Composable
fun rememberMoneyInputState(initial: Money? = null): MoneyInputState =
    MoneyInputState(rememberTextFieldState(initial?.cents?.toString().orEmpty()))

@JvmInline
value class MoneyInputState(val backingState: TextFieldState) {
    val money: Money?
        get() = backingState.text.filter(Char::isDigit).toString().toLongOrNull()
            ?.let { Money(cents = it) }

    fun isSet(): Boolean = money != null
}

@Composable
@Preview(showBackground = true)
private fun PreviewMoneyInput() {
    val state = rememberMoneyInputState(Money(199))
    MoneyInput(
        state = state,
        label = { Text("Price") }
    )
}
