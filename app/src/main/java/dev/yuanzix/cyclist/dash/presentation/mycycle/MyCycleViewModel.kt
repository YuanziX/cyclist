package dev.yuanzix.cyclist.dash.presentation.mycycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import dev.yuanzix.cyclist.dash.domain.BicycleDataSource
import dev.yuanzix.cyclist.dash.domain.openNavigationLink
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class MyCycleViewModel(
    private val bicycleDataSource: BicycleDataSource,
    private val dataStore: UserPreferencesDataStore,
) : ViewModel() {
    private val _state = MutableStateFlow(MyCycleState())
    val state = _state.onStart {
        getMyBicycle()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MyCycleState())

    private val _events = Channel<MyCycleEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: MyCycleAction) {
        when (action) {
            MyCycleAction.LockUnlockCycle -> {
                unlockBicycle()
            }

            is MyCycleAction.LocateCycle -> {
                action.ctx.openNavigationLink(
                    _state.value.bicycle!!.location.lat, _state.value.bicycle!!.location.lng
                )
            }
        }
    }

    private fun getMyBicycle() {
        viewModelScope.launch {
            bicycleDataSource.getRentedBicycle(
                token = dataStore.jwtToken.first()!!
            ).onSuccess { res ->
                _state.update { st ->
                    st.copy(
                        isLoading = false,
                        rentalId = res.rentalId,
                        bicycle = res.bike,
                        endTime = ZonedDateTime.parse(res.endTime)
                    )
                }
            }.onError { error ->
                _state.update { st ->
                    st.copy(
                        isLoading = false,
                    )
                }
                _events.send(MyCycleEvent.Error(error))
            }
        }
    }

    private fun unlockBicycle() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isAwaitingUnlock = true
                )
            }
            bicycleDataSource.unlockBicycle(
                rentalId = _state.value.rentalId!!,
                token = dataStore.jwtToken.first()!!,
            ).onSuccess {
                val res = bicycleDataSource.sendSignalToLock(
                    url = _state.value.bicycle!!.serverUrl, commsToken = "onegai"
                )

                // anti pattern but i'm tired of this "project" already
                // was gonna call stringResource here but that's an anti pattern too
                if (res) {
                    _events.send(MyCycleEvent.ShowMessage("Cycle unlocked successfully"))
                } else {
                    _events.send(MyCycleEvent.ShowMessage("Could not unlock cycle"))
                }
            }.onError {
                _events.send(MyCycleEvent.Error(it))
            }
            _state.update {
                it.copy(
                    isAwaitingUnlock = false
                )
            }
        }
    }

}