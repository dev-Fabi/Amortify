package dev.schedler.amortify.domain.model

import kotlin.time.Instant
import kotlin.uuid.Uuid

data class UsageEntryModel(
    val id: Uuid? = null, // null if not persisted yet
    val dateTime: Instant,
    val description: String,
    val price: Money,
)