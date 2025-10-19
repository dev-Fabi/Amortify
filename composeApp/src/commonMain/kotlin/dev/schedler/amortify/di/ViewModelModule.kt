package dev.schedler.amortify.di

import dev.schedler.amortify.presentation.cardlist.CardListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CardListViewModel(cardRepository = get(), usageEntryRepository = get()) }
}
