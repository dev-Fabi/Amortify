package dev.schedler.amortify.data.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.schedler.amortify.data.local.dao.CardDao
import dev.schedler.amortify.data.local.dao.UsageEntryDao
import dev.schedler.amortify.data.local.dao.UsageTemplateDao
import dev.schedler.amortify.data.local.db.entities.CardEntity
import dev.schedler.amortify.data.local.db.entities.UsageEntryEntity
import dev.schedler.amortify.data.local.db.entities.UsageTemplateEntity

@Database(
    entities = [
        CardEntity::class,
        UsageEntryEntity::class,
        UsageTemplateEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
@ConstructedBy(AmortifyDatabaseConstructor::class)
abstract class AmortifyDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun usageEntryDao(): UsageEntryDao
    abstract fun usageTemplateDao(): UsageTemplateDao

    companion object {
        fun create(
            builder: Builder<AmortifyDatabase>
        ): AmortifyDatabase {
            return builder
                .setDriver(BundledSQLiteDriver())
                .build()
        }
    }
}

@Suppress("KotlinNoActualForExpect")
expect object AmortifyDatabaseConstructor : RoomDatabaseConstructor<AmortifyDatabase> {
    override fun initialize(): AmortifyDatabase
}
