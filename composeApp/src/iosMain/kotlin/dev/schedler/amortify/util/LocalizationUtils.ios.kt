package dev.schedler.amortify.util

actual object LocalizationUtils {
    actual val decimalSeparator: Char = NSLocal().decimalSeperator.first()
}