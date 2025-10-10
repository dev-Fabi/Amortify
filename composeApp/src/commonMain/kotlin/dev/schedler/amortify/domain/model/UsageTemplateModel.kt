package dev.schedler.amortify.domain.model

import kotlin.uuid.Uuid

data class UsageTemplateModel(
    val id: Uuid? = null, // null if not persisted yet
    val description: String,
    val price: Money
)