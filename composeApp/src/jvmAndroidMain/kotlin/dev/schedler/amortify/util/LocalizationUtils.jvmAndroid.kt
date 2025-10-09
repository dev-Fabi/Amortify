package dev.schedler.amortify.util

import java.text.DecimalFormat

actual object LocalizationUtils {
    actual val decimalSeparator: Char =
        DecimalFormat().decimalFormatSymbols.monetaryDecimalSeparator
}