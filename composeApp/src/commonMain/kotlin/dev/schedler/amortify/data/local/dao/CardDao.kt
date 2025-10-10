package dev.schedler.amortify.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.schedler.amortify.data.local.db.entities.CardEntity
import dev.schedler.amortify.data.local.db.relations.CardWithDetails
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface CardDao {
    @Query("SELECT * FROM cards")
    fun getAllCards(): Flow<List<CardEntity>>

    @Transaction
    @Query("SELECT * FROM cards")
    fun getAllCardsWithDetails(): Flow<List<CardWithDetails>>

    @Transaction
    @Query("SELECT * FROM cards WHERE id = :id")
    fun getCardWithDetails(id: Uuid): Flow<CardWithDetails?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity)

    @Query("DELETE FROM cards WHERE id = :id")
    suspend fun deleteCard(id: Uuid)
}
