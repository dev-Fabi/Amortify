package dev.schedler.amortify.domain.model

data class UsageTemplateModel(
    val id: Long? = null, // null if not persisted yet
    val description: String,
    val price: Money
)