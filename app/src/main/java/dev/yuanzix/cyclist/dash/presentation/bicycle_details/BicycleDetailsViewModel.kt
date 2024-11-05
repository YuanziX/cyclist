package dev.yuanzix.cyclist.dash.presentation.bicycle_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import dev.yuanzix.cyclist.dash.domain.BicycleDataSource
import dev.yuanzix.cyclist.dash.domain.toCustomString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration

class BicycleDetailsViewModel(
    private val bikeId: String,
    private val bicycleDataSource: BicycleDataSource,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(BicycleDetailsState())
    val state = _state.onStart { getBike() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BicycleDetailsState())


    private val _events = Channel<BicycleDetailsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: BicycleDetailsAction) {
        when (action) {
            is BicycleDetailsAction.SetStartTime -> {
                _state.update {
                    it.copy(startTime = action.time)
                }
                println(action.time.toString())
            }

            is BicycleDetailsAction.SetEndTime -> {
                _state.update {
                    it.copy(endTime = action.time)
                }
            }

            BicycleDetailsAction.RentBike -> {
                rentBike()
            }
        }
    }

    private fun getBike() {
        viewModelScope.launch {
            bicycleDataSource.getBicycleById(bikeId).onSuccess { bike ->
                _state.update {
                    it.copy(
                        isLoading = false, bicycle = bike
                    )
                }
            }.onError { _ ->
                _events.send(BicycleDetailsEvent.CouldNotFetchBicycleData)
            }
        }
    }

    private fun rentBike() {
        viewModelScope.launch {
            println(Duration.between(state.value.startTime, state.value.endTime).toHours())
            if (Duration.between(state.value.startTime, state.value.endTime).toHours() < 1) {
                _events.send(BicycleDetailsEvent.ShortDurationNotAllowed)
                return@launch
            }

            bicycleDataSource.rentBicycle(
                bikeId = state.value.bicycle!!.id,
                startTime = state.value.startTime.toCustomString(),
                endTime = state.value.endTime.toCustomString(),
                token = dataStore.jwtToken.first()!!,
            ).onSuccess {
                _events.send(BicycleDetailsEvent.RentedSuccessfully)
            }.onError {
                _events.send(BicycleDetailsEvent.Error(it))
            }
        }
    }
}