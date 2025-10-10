package dev.schedler.amortify.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.schedler.amortify.domain.model.Money
import kotlinx.datetime.LocalDate
import kotlin.uuid.Uuid

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: Uuid,
    val name: String,
    val price: Money,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val baseColor: Long
)
