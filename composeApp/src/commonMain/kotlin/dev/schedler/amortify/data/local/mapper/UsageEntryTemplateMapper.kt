package dev.schedler.amortify.data.local.mapper

import dev.schedler.amortify.data.local.db.entities.UsageTemplateEntity
import dev.schedler.amortify.domain.model.UsageTemplateModel
import kotlin.uuid.Uuid


fun UsageTemplateEntity.toDomain() = UsageTemplateModel(
    id = id,
    description = description,
    price = price
)

fun List<UsageTemplateEntity>.toDomain() = map(UsageTemplateEntity::toDomain)

fun UsageTemplateModel.toEntity(cardId: Uuid) = UsageTemplateEntity(
    id = id ?: Uuid.random(),
    cardId = cardId,
    description = description,
    price = price
)