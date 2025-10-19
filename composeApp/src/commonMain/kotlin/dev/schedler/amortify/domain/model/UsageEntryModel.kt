package dev.schedler.amortify.domain.model

import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class UsageEntryModel(
    val id: Uuid?, // null if not persisted yet
    val dateTime: Instant,
    val description: String,
    val price: Money,
) {
    companion object {
        fun fromTemplate(template: UsageTemplateModel) = UsageEntryModel(
            id = null,
            dateTime = Clock.System.now(),
            description = template.description,
            price = template.price
        )
    }
}