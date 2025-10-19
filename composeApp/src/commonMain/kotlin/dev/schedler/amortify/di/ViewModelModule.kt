package dev.schedler.amortify.di

import dev.schedler.amortify.presentation.carddetail.CardDetailViewModel
import dev.schedler.amortify.presentation.cardlist.CardListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.uuid.Uuid

val viewModelModule = module {
    viewModel { CardListViewModel(cardRepository = get(), usageEntryRepository = get()) }
    viewModel { (cardId: Uuid) ->
        CardDetailViewModel(cardId, cardRepository = get(), usageEntryRepository = get())
    }
}
