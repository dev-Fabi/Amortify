package dev.schedler.amortify.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class UsageEntryModel @OptIn(ExperimentalTime::class) constructor(
    val dateTime: Instant,
    val description: String,
    val price: Money,
)