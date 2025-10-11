package dev.schedler.amortify.data.repository

import dev.schedler.amortify.data.local.dao.CardDao
import dev.schedler.amortify.data.local.mapper.toDomain
import dev.schedler.amortify.data.local.mapper.toEntity
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.SimpleCardModel
import dev.schedler.amortify.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.Uuid

class CardRepositoryImpl(
    private val cardDao: CardDao,
) : CardRepository {

    override fun getAllCards(): Flow<List<CardModel>> =
        cardDao.getAllCardsWithDetails().map { cardsWithRelations ->
            cardsWithRelations.toDomain()
        }

    override fun getCardById(id: Uuid): Flow<CardModel?> =
        cardDao.getCardWithDetails(id).map { it?.toDomain() }

    override suspend fun insertCard(card: SimpleCardModel) {
        cardDao.insertCard(card.toEntity())
    }

    override suspend fun updateCard(card: SimpleCardModel) {
        if (card.id == null) {
            throw IllegalArgumentException("Card ID must not be null when updating a card.")
        }
        cardDao.updateCard(card.toEntity())
    }

    override suspend fun deleteCard(id: Uuid) {
        cardDao.deleteCard(id)
    }
}
