package dev.schedler.amortify.data.repository

import dev.schedler.amortify.data.local.dao.UsageEntryDao
import dev.schedler.amortify.data.local.mapper.toDomain
import dev.schedler.amortify.data.local.mapper.toEntity
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.domain.repository.UsageEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.Uuid

class UsageEntryRepositoryImpl(
    private val usageEntryDao: UsageEntryDao
) : UsageEntryRepository {
    override fun getById(id: Uuid): Flow<UsageEntryModel?> {
        return usageEntryDao.getById(id).map { it?.toDomain() }
    }

    override fun getForCard(cardId: Uuid): Flow<List<UsageEntryModel>> {
        return usageEntryDao.getByCard(cardId).map { it.toDomain() }
    }

    override suspend fun insert(cardId: Uuid, usage: UsageEntryModel) {
        usageEntryDao.insert(usage.toEntity(cardId))
    }

    override suspend fun update(cardId: Uuid, usage: UsageEntryModel) {
        usageEntryDao.update(usage.toEntity(cardId))
    }

    override suspend fun delete(id: Uuid) {
        usageEntryDao.delete(id)
    }
}