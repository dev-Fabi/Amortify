package dev.schedler.amortify.domain.model

import kotlinx.datetime.LocalDateTime

data class UsageEntryModel(
    val dateTime: LocalDateTime,
    val description: String,
    val price: Money,
)