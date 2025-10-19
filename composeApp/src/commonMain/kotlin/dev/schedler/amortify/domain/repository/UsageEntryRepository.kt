package dev.schedler.amortify.domain.repository

import dev.schedler.amortify.domain.model.UsageEntryModel
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface UsageEntryRepository {
    fun getById(id: Uuid): Flow<UsageEntryModel?>
    fun getForCard(cardId: Uuid): Flow<List<UsageEntryModel>>
    suspend fun insert(cardId: Uuid, usage: UsageEntryModel)
    suspend fun update(cardId: Uuid, usage: UsageEntryModel)
    suspend fun delete(id: Uuid)
}