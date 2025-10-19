package dev.schedler.amortify.presentation.carddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.UsageEntryModel
import dev.schedler.amortify.domain.repository.CardRepository
import dev.schedler.amortify.domain.repository.UsageEntryRepository
import dev.schedler.amortify.presentation.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

class CardDetailViewModel(
    private val cardId: Uuid,
    private val cardRepository: CardRepository,
    private val usageEntryRepository: UsageEntryRepository,
) : ViewModel() {

    private val _card = MutableStateFlow<Resource<CardModel?>>(Resource.Loading)
    val card: StateFlow<Resource<CardModel?>> = _card.asStateFlow()

    init {
        observeCard()
    }

    private fun observeCard() {
        viewModelScope.launch {
            cardRepository.getCardById(cardId)
                .map<CardModel?, Resource<CardModel?>> { Resource.Success(it) }
                .onStart { emit(Resource.Loading) }
                //.catch { e -> emit(Result.Error(e.message ?: "Unknown error", e)) }
                .collect { _card.value = it }
        }
    }

    fun saveUsage(usage: UsageEntryModel) {
        viewModelScope.launch {
            if (usage.id == null) {
                usageEntryRepository.insert(cardId, usage)
            } else {
                usageEntryRepository.update(cardId, usage)
            }
        }
    }

    fun deleteUsage(id: Uuid) {
        viewModelScope.launch {
            usageEntryRepository.delete(id)
        }
    }
}
