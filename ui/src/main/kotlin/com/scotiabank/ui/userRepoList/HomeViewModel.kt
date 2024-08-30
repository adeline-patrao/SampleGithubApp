package com.scotiabank.ui.userRepoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scotiabank.domain.model.UserRepo
import com.scotiabank.domain.usecase.GetUserRepoUseCase
import com.scotiabank.ui.util.UiState
import com.scotiabank.ui.util.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getUserRepoUseCase: GetUserRepoUseCase) :
    ViewModel() {
    private var _userRepoUiState: MutableStateFlow<UiState<UserRepo>> =
        MutableStateFlow(UiState.Loading)
    val userRepoUiState: StateFlow<UiState<UserRepo>> = _userRepoUiState

    fun getUserRepo(userId: String) {
        viewModelScope.launch {
            getUserRepoUseCase(userId).asUiState().collectLatest {
                _userRepoUiState.value = it
            }
        }
    }
}