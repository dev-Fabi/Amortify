package dev.schedler.amortify.domain.repository

import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.SimpleCardModel
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface CardRepository {
    fun getAllCards(): Flow<List<CardModel>>
    fun getCardById(id: Uuid): Flow<CardModel?>
    suspend fun insertCard(card: SimpleCardModel)
    suspend fun updateCard(card: SimpleCardModel)
    suspend fun deleteCard(id: Uuid)
}
