package com.scotiabank.ui.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data object Loading : UiState<Nothing>
    data class Error(val message: String?) : UiState<Nothing>
}

fun <T> Flow<T>.asUiState(errorMessage: String? = null): Flow<UiState<T>> =
    map<T, UiState<T>> {
        UiState.Success(it)
    }.catch {
        emit(UiState.Error(errorMessage ?: it.message))
    }
