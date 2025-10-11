package dev.schedler.amortify.presentation.util

import org.jetbrains.compose.resources.StringResource

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: StringResource) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}