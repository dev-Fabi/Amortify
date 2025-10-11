package dev.schedler.amortify.di

import androidx.room.RoomDatabase
import dev.schedler.amortify.data.local.dao.CardDao
import dev.schedler.amortify.data.local.dao.UsageEntryDao
import dev.schedler.amortify.data.local.dao.UsageTemplateDao
import dev.schedler.amortify.data.local.db.AmortifyDatabase
import org.koin.dsl.module

fun databaseModule(databaseBuilder: RoomDatabase.Builder<AmortifyDatabase>) = module {

    single { AmortifyDatabase.create(databaseBuilder) }

    single<CardDao> { get<AmortifyDatabase>().cardDao() }
    single<UsageEntryDao> { get<AmortifyDatabase>().usageEntryDao() }
    single<UsageTemplateDao> { get<AmortifyDatabase>().usageTemplateDao() }
}