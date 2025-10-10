package dev.schedler.amortify.data.local.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.schedler.amortify.data.local.db.entities.CardEntity
import dev.schedler.amortify.data.local.db.entities.UsageEntryEntity
import dev.schedler.amortify.data.local.db.entities.UsageTemplateEntity

data class CardWithDetails(
    @Embedded val card: CardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "cardId"
    )
    val usages: List<UsageEntryEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "cardId"
    )
    val usageTemplates: List<UsageTemplateEntity>
)
