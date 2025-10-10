package dev.schedler.amortify.data.local.db

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import dev.schedler.amortify.domain.model.Money
import kotlinx.datetime.LocalDate
import kotlin.time.Instant
import kotlin.uuid.Uuid

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)


    @TypeConverter
    fun fromInstant(instant: Instant): Long = instant.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(value: Long): Instant = Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun fromColor(color: Color): ULong = color.value

    @TypeConverter
    fun toColor(value: ULong): Color = Color(value)

    @TypeConverter
    fun fromMoney(money: Money): Long = money.cents

    @TypeConverter
    fun toMoney(value: Long): Money = Money(value)

    @TypeConverter
    fun fromUuid(uuid: Uuid): String = uuid.toHexDashString()

    @TypeConverter
    fun toUuid(value: String): Uuid = Uuid.parseHexDash(value)
}
