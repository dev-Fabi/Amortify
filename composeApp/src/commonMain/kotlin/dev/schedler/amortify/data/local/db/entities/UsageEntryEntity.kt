package dev.schedler.amortify.data.local.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.schedler.amortify.domain.model.Money
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Entity(
    tableName = "usage_entries",
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
data class UsageEntryEntity(
    @PrimaryKey val id: Uuid,
    val cardId: Uuid,
    val dateTime: Instant,
    val description: String,
    val price: Money
)
