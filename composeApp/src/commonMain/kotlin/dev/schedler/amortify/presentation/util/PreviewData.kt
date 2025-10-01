package dev.schedler.amortify.presentation.util

import androidx.compose.ui.graphics.Color
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.Money
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.domain.model.UsageTemplate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal object PreviewData {
    val usageEntries = listOf(
        UsageEntryModel(
            LocalDateTime(2025, 9, 30, 18, 30),
            "Graz – Vienna (Train)",
            Money(units = 29, cents = 0)
        ),
        UsageEntryModel(
            LocalDateTime(2025, 9, 29, 7, 15),
            "Morning Swim – 2h Ticket",
            Money(units = 3, cents = 50)
        ),
        UsageEntryModel(
            LocalDateTime(2025, 9, 25, 19, 0),
            "Evening Ticket",
            Money(units = 5, cents = 0)
        ),
        UsageEntryModel(
            LocalDateTime(2025, 9, 20, 10, 0),
            "Museum Entry",
            Money(units = 5, cents = 0)
        )
    )

    val usageTemplates = listOf(
        UsageTemplate("2h Ticket", Money(units = 3, cents = 50)),
        UsageTemplate("Evening Ticket", Money(units = 5, cents = 0)),
        UsageTemplate("Graz – Vienna", Money(units = 29, cents = 0))
    )

    val gymCard = CardModel(
        "Gym year pass",
        Money(units = 59 * 12, cents = 99 * 12),
        LocalDate(2025, 1, 1),
        LocalDate(2025, 12, 31),
        Color(0xFF4CAF50),
        usageEntries,
        usageTemplates,
    )

    val poolCard = CardModel(
        "Swimming season ticket",
        Money(units = 305, cents = 50),
        LocalDate(2025, 5, 1),
        LocalDate(2025, 9, 7),
        Color(0xFF2196F3),
        usageEntries,
        emptyList(),
    )

    val skiPass = CardModel(
        "Skipass Winter 2025",
        Money(units = 899, cents = 99),
        LocalDate(2025, 12, 1),
        LocalDate(2026, 3, 31),
        Color(0xFFFF9800),
        usageEntries,
        usageTemplates,
    )

    val transportCard = CardModel(
        "Climate ticket Austria",
        Money(units = 1050, cents = 0),
        LocalDate(2025, 7, 1),
        LocalDate(2026, 6, 30),
        Color(0xFF9C27B0),
        usageEntries,
        usageTemplates,
    )

    val boulderCard = CardModel(
        "Bouldering 25/26WS",
        Money(units = 280, cents = 0),
        LocalDate(2025, 10, 1),
        LocalDate(2026, 2, 28),
        Color(0xFFE91E63),
        usageEntries,
        emptyList(),
    )
}