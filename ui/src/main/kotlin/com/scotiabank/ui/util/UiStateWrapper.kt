package com.scotiabank.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> UiStateWrapper(
    uiState: UiState<T>,
    onLoading: @Composable () -> Unit = { Loading() },
    onError: @Composable (String) -> Unit = { Error(it) },
    onSuccess: @Composable (T) -> Unit,
) {
    when (uiState) {
        UiState.Loading -> onLoading()
        is UiState.Error -> onError(uiState.message ?: "Unknown Error")
        is UiState.Success -> onSuccess(uiState.data)
    }
}


@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = errorMessage, modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}