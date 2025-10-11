package dev.schedler.amortify.di

import dev.schedler.amortify.data.repository.CardRepositoryImpl
import dev.schedler.amortify.domain.repository.CardRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CardRepository> { CardRepositoryImpl(get()) }
}
