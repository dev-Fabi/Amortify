package dev.schedler.amortify.data.local.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.schedler.amortify.domain.model.Money
import kotlin.uuid.Uuid

@Entity(
    tableName = "usage_templates",
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cardId")]
)
data class UsageTemplateEntity(
    @PrimaryKey val id: Uuid,
    val cardId: Uuid,
    val description: String,
    val price: Money
)
