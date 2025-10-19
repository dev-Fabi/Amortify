package dev.schedler.amortify.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.schedler.amortify.data.local.db.entities.UsageEntryEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface UsageEntryDao {
    @Query("SELECT * FROM usage_entries WHERE id = :id")
    fun getById(id: Uuid): Flow<UsageEntryEntity?>

    @Query("SELECT * FROM usage_entries WHERE cardId = :cardId")
    fun getByCard(cardId: Uuid): Flow<List<UsageEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: UsageEntryEntity)

    @Update
    suspend fun update(entry: UsageEntryEntity)

    @Query("DELETE FROM usage_entries WHERE id = :id")
    suspend fun delete(id: Uuid)
}
