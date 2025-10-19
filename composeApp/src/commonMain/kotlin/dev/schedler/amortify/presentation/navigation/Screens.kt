package dev.schedler.amortify.presentation.navigation

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

sealed interface Screen {
    @Serializable
    object CardList : Screen

    @Serializable
    data class CardDetail(val cardId: Uuid) : Screen
}