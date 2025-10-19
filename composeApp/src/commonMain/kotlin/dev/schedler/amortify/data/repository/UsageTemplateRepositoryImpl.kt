package dev.schedler.amortify.data.repository

import dev.schedler.amortify.data.local.dao.UsageTemplateDao
import dev.schedler.amortify.data.local.mapper.toDomain
import dev.schedler.amortify.data.local.mapper.toEntity
import dev.schedler.amortify.domain.model.UsageTemplateModel
import dev.schedler.amortify.domain.repository.UsageTemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.Uuid

class UsageTemplateRepositoryImpl(
    private val usageTemplateDao: UsageTemplateDao
) : UsageTemplateRepository {
    override fun getById(id: Uuid): Flow<UsageTemplateModel?> {
        return usageTemplateDao.getById(id).map { it?.toDomain() }
    }

    override fun getForCard(cardId: Uuid): Flow<List<UsageTemplateModel>> {
        return usageTemplateDao.getByCard(cardId).map { it.toDomain() }
    }

    override suspend fun insert(cardId: Uuid, usage: UsageTemplateModel) {
        usageTemplateDao.insert(usage.toEntity(cardId))
    }

    override suspend fun update(cardId: Uuid, usage: UsageTemplateModel) {
        if (usage.id == null) {
            throw IllegalArgumentException("UsageTemplate must not be null when updating.")
        }
        usageTemplateDao.update(usage.toEntity(cardId))
    }

    override suspend fun delete(id: Uuid) {
        usageTemplateDao.delete(id)
    }
}