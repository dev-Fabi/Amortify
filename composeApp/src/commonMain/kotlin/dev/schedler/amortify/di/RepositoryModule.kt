package dev.schedler.amortify.di

import dev.schedler.amortify.data.repository.CardRepositoryImpl
import dev.schedler.amortify.data.repository.UsageEntryRepositoryImpl
import dev.schedler.amortify.data.repository.UsageTemplateRepositoryImpl
import dev.schedler.amortify.domain.repository.CardRepository
import dev.schedler.amortify.domain.repository.UsageEntryRepository
import dev.schedler.amortify.domain.repository.UsageTemplateRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CardRepository> { CardRepositoryImpl(get()) }
    single<UsageEntryRepository> { UsageEntryRepositoryImpl(get()) }
    single<UsageTemplateRepository> { UsageTemplateRepositoryImpl(get()) }
}
