package com.scotiabank.ui.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class UiStateTest {

    @Test
    fun `asUiState should emit Success when flow emits data`() = runTest {
        // Given
        val dataFlow: Flow<Int> = flowOf(1, 2, 3)

        // When
        val uiStateFlow = dataFlow.asUiState()

        // Then
        val expected = listOf(
            UiState.Success(1),
            UiState.Success(2),
            UiState.Success(3)
        )
        val actual = uiStateFlow.toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `asUiState should emit Error when flow throws exception`() = runTest {
        // Given
        val errorMessage = "Test Error"
        val dataFlow: Flow<Int> = flow {
            emit(1)
            throw RuntimeException("Test Exception")
        }

        // When
        val uiStateFlow = dataFlow.asUiState(errorMessage)

        // Then
        val expected = listOf(
            UiState.Success(1),
            UiState.Error(errorMessage)
        )
        val actual = uiStateFlow.toList()
        assertEquals(expected, actual)
    }
}