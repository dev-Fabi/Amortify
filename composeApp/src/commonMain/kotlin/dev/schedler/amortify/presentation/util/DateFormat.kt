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

    val dateOnly = LocalDateTime.Format {
        day()
        char('.')
        monthNumber()
        char('.')
        year()
    }

    val timeOnly = LocalDateTime.Format {
        hour()
        char(':')
        minute()
    }

    val date = LocalDate.Format {
        day()
        char('.')
        monthNumber()
        char('.')
        year()
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