package dev.schedler.amortify.domain.repository

import dev.schedler.amortify.domain.model.UsageTemplateModel
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface UsageTemplateRepository {
    fun getById(id: Uuid): Flow<UsageTemplateModel?>
    fun getForCard(cardId: Uuid): Flow<List<UsageTemplateModel>>
    suspend fun insert(cardId: Uuid, usage: UsageTemplateModel)
    suspend fun update(cardId: Uuid, usage: UsageTemplateModel)
    suspend fun delete(id: Uuid)
}