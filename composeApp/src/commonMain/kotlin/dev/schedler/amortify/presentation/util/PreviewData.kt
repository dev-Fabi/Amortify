package dev.schedler.amortify.presentation.util

import androidx.compose.ui.graphics.Color
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.Money
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.domain.model.UsageTemplateModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

internal object PreviewData {
    @OptIn(ExperimentalTime::class)
    val usageEntries = listOf(
        UsageEntryModel(
            id = 1,
            LocalDateTime(2025, 9, 30, 18, 30).toInstant(TimeZone.currentSystemDefault()),
            "Graz – Vienna (Train)",
            Money(units = 29, cents = 0)
        ),
        UsageEntryModel(
            id = 2,
            LocalDateTime(2025, 9, 29, 7, 15).toInstant(TimeZone.currentSystemDefault()),
            "Morning Swim – 2h Ticket",
            Money(units = 3, cents = 50)
        ),
        UsageEntryModel(
            id = 3,
            LocalDateTime(2025, 9, 25, 19, 0).toInstant(TimeZone.currentSystemDefault()),
            "Evening Ticket",
            Money(units = 5, cents = 0)
        ),
        UsageEntryModel(
            id = 4,
            LocalDateTime(2025, 9, 20, 10, 0).toInstant(TimeZone.currentSystemDefault()),
            "Museum Entry",
            Money(units = 5, cents = 0)
        )
    )

    val usageTemplates = listOf(
        UsageTemplateModel(id = 1, "2h Ticket", Money(units = 3, cents = 50)),
        UsageTemplateModel(id = 2, "Evening Ticket", Money(units = 5, cents = 0)),
        UsageTemplateModel(id = 3, "Graz – Vienna", Money(units = 29, cents = 0))
    )

    val gymCard = CardModel(
        id = 1,
        "Gym year pass",
        Money(units = 59 * 12, cents = 99 * 12),
        LocalDate(2025, 1, 1),
        LocalDate(2025, 12, 31),
        Color(0xFF4CAF50),
        usageEntries,
        usageTemplates,
    )

    val poolCard = CardModel(
        id = 2,
        "Swimming season ticket",
        Money(units = 305, cents = 50),
        LocalDate(2025, 5, 1),
        LocalDate(2025, 9, 7),
        Color(0xFF2196F3),
        usageEntries,
        emptyList(),
    )

    val skiPass = CardModel(
        id = 3,
        "Skipass Winter 2025",
        Money(units = 899, cents = 99),
        LocalDate(2025, 12, 1),
        LocalDate(2026, 3, 31),
        Color(0xFFFF9800),
        usageEntries,
        usageTemplates,
    )

    val transportCard = CardModel(
        id = 4,
        "Climate ticket Austria",
        Money(units = 1050, cents = 0),
        LocalDate(2025, 7, 1),
        LocalDate(2026, 6, 30),
        Color(0xFF9C27B0),
        usageEntries,
        usageTemplates,
    )

    val boulderCard = CardModel(
        id = 5,
        "Bouldering 25/26WS",
        Money(units = 280, cents = 0),
        LocalDate(2025, 10, 1),
        LocalDate(2026, 2, 28),
        Color(0xFFE91E63),
        usageEntries,
        emptyList(),
    )
}