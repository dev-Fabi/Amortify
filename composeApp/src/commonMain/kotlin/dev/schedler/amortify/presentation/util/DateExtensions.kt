package dev.schedler.amortify.presentation.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun LocalDate.Companion.now(): LocalDate {
    val now = Clock.System.now()
    return now.toLocalDateTime(TimeZone.currentSystemDefault()).date
}