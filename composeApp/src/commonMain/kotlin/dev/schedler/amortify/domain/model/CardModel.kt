package dev.schedler.amortify.domain.model

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate

data class CardModel(
    val name: String,
    val price: Money,
    val start: LocalDate,
    val end: LocalDate,
    val baseColor: Color,
    val usages: List<UsageEntryModel>,
    val usageTemplates: List<UsageTemplate>,
) {
    val used: Money = usages.fold(Money(0)) { acc, usage -> acc + usage.price }
    val progress: Float = used.cents.toFloat() / price.cents
}