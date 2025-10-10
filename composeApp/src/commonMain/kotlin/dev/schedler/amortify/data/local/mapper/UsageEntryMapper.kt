package dev.schedler.amortify.data.local.mapper

import dev.schedler.amortify.data.local.db.entities.UsageEntryEntity
import dev.schedler.amortify.domain.model.UsageEntryModel
import kotlin.uuid.Uuid

fun UsageEntryEntity.toDomain(): UsageEntryModel = UsageEntryModel(
    id = id,
    dateTime = dateTime,
    description = description,
    price = price
)

fun List<UsageEntryEntity>.toDomain(): List<UsageEntryModel> = this.map(UsageEntryEntity::toDomain)

fun UsageEntryModel.toEntity(cardId: Uuid) = UsageEntryEntity(
    id = id ?: Uuid.random(),
    cardId = cardId,
    dateTime = dateTime,
    description = description,
    price = price
)