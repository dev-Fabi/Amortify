package dev.schedler.amortify.presentation.cardlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.schedler.amortify.domain.model.CardModel
import dev.schedler.amortify.domain.model.SimpleCardModel
import dev.schedler.amortify.domain.repository.CardRepository
import dev.schedler.amortify.presentation.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

class CardListViewModel(
    private val repository: CardRepository,
) : ViewModel() {

    private val _cards = MutableStateFlow<Resource<List<CardModel>>>(Resource.Loading)
    val cards: StateFlow<Resource<List<CardModel>>> = _cards.asStateFlow()

    init {
        observeCards()
    }

    private fun observeCards() {
        viewModelScope.launch {
            repository.getAllCards()
                .map<List<CardModel>, Resource<List<CardModel>>> { Resource.Success(it) }
                .onStart { emit(Resource.Loading) }
                //.catch { e -> emit(Resource.Error(StringResource e.message ?: "Unknown error", e)) }
                .collect { _cards.value = it }
        }
    }

    fun saveCard(card: SimpleCardModel) {
        if (card.id == null) {
            addCard(card)
        } else {
            updateCard(card)
        }
    }

    private fun addCard(card: SimpleCardModel) {
        viewModelScope.launch {
            runCatching { repository.insertCard(card) }
            //.onFailure { e -> _cards.value = Resource.Error("Failed to add card", e) }
        }
    }

    private fun updateCard(card: SimpleCardModel) {
        viewModelScope.launch {
            runCatching { repository.updateCard(card) }
            //.onFailure { e -> _cards.value = Resource.Error("Failed to update card", e) }
        }
    }

    fun deleteCard(id: Uuid) {
        viewModelScope.launch {
            runCatching { repository.deleteCard(id) }
            //.onFailure { e -> _cards.value = Result.Error("Failed to delete card", e) }
        }
    }
}
