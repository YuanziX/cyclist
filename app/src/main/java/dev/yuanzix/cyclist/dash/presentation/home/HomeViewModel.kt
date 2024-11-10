package dev.yuanzix.cyclist.dash.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import dev.yuanzix.cyclist.dash.domain.BicycleDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bicycleDataSource: BicycleDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        loadBicycles()
        _state.update { it.copy(isLoading = false) }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: HomeAction) {
        viewModelScope.launch {
            when (action) {
                is HomeAction.NavigateToCycleWithID -> _events.send(
                    HomeEvent.NavigateToCycleWithID(
                        action.id
                    )
                )
            }
        }
    }

    fun loadBicycles() {
        viewModelScope.launch {
            bicycleDataSource.getBicycles(_state.value.currentPage + 1)
                .onSuccess { data ->
                    if (data.page == data.totalPages) {
                        _state.update {
                            it.copy(
                                // it.bicycles + data.bikes
                                bicycles = data.bikes,
                                isLastPage = true
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                bicycles = it.bicycles + data.bikes,
                                currentPage = data.page
                            )
                        }
                    }
                }
                .onError { error ->
                    _events.send(HomeEvent.Error(error))
                }
        }
    }
}