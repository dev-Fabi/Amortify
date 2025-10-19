package dev.schedler.amortify.util

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.decimalSeparator

actual object LocalizationUtils {
    actual val decimalSeparator: Char = NSLocale.currentLocale.decimalSeparator.first()
}