package com.dx.alumnicasestudy.ui.screens.home

import androidx.lifecycle.ViewModel
import com.dx.alumnicasestudy.core.Resource
import com.dx.alumnicasestudy.data.domain.models.User
import com.dx.alumnicasestudy.data.domain.useCase.GetUsersUseCase
import com.dx.alumnicasestudy.data.domain.util.OrderBy
import com.dx.alumnicasestudy.data.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())

    val state = _state.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        getUsersUseCase(state.value.orderBy).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = HomeUiState(true)
                }
                is Resource.Success -> {
//                    _state.value = HomeUiState(false, users = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = HomeUiState(false, error = result.msg)
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    var orderBy: OrderBy = OrderBy.TechStack(OrderType.Ascending),
    val error: String? = ""
)