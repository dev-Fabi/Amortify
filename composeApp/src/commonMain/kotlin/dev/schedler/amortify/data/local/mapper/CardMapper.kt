package dev.schedler.amortify.data.local.mapper

import androidx.compose.ui.graphics.Color
import dev.schedler.amortify.data.local.db.entities.CardEntity
import dev.schedler.amortify.data.local.db.relations.CardWithDetails
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.SimpleCardModel
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.domain.model.UsageTemplateModel
import kotlin.uuid.Uuid

fun CardEntity.toDomain(
    usages: List<UsageEntryModel>,
    templates: List<UsageTemplateModel>
) = CardModel(
    id = id,
    name = name,
    price = price,
    start = startDate,
    end = endDate,
    baseColor = Color(baseColor.toULong()),
    usages = usages,
    usageTemplates = templates
)

fun CardWithDetails.toDomain() = card.toDomain(usages.toDomain(), usageTemplates.toDomain())
fun List<CardWithDetails>.toDomain() = map(CardWithDetails::toDomain)

fun SimpleCardModel.toEntity() = CardEntity(
    id = id ?: Uuid.random(),
    name = name,
    price = price,
    startDate = start,
    endDate = end,
    baseColor = baseColor.value.toLong()
)
