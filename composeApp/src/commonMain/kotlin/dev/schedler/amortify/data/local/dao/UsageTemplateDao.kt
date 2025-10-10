package dev.schedler.amortify.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.schedler.amortify.data.local.db.entities.UsageTemplateEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface UsageTemplateDao {
    @Query("SELECT * FROM usage_templates WHERE cardId = :cardId")
    fun getByCard(cardId: Uuid): Flow<List<UsageTemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(template: UsageTemplateEntity)

    @Query("DELETE FROM usage_templates WHERE id = :id")
    suspend fun delete(id: Uuid)
}
