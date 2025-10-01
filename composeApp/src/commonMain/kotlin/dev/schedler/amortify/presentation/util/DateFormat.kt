package dev.schedler.amortify.presentation.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

object DateFormat {
    val default = LocalDateTime.Format {
        day()
        char('.')
        monthNumber()
        char('.')
        year()
        chars(" • ")
        hour()
        char(':')
        minute()
    }

    fun fromTo(from: LocalDate, to: LocalDate): String {
        val dateFormat = LocalDate.Format {
            day()
            char('.')
            monthNumber()
            char('.')
            year()
        }
        return "${from.format(dateFormat)} - ${to.format(dateFormat)}"
    }
}