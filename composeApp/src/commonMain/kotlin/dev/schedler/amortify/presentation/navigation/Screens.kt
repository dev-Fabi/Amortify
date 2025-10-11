package dev.schedler.amortify.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    object CardList : Screen

    @Serializable
    data class CardDetail(val cardId: String) : Screen
}