package dev.yuanzix.cyclist.core.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.auth.domain.AuthRepository
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import dev.yuanzix.cyclist.core.navigation.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartViewModel(
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    private val _events = Channel<StartEvent>()
    val events = _events.receiveAsFlow()

    init {
        verifyCreds()
    }

    fun onAction(event: StartAction) {
        when (event) {
            is StartAction.OnPermissionResult -> handlePermissionResult(event)
            is StartAction.DismissPermissionDialog -> dismissPermissionDialog()
            is StartAction.OnAllPermissionsGranted -> grantAllPermissions()
        }
    }

    private fun handlePermissionResult(event: StartAction.OnPermissionResult) {
        _state.update { current ->
            if (!event.isGranted && !current.visiblePermissionDialogQueue.contains(event.permission)) {
                current.copy(
                    visiblePermissionDialogQueue = current.visiblePermissionDialogQueue + event.permission
                )
            } else current
        }

        if (_state.value.allPermissionsGranted && _state.value.nextNavigationRoute != null) {
            goAhead()
        }
    }

    private fun dismissPermissionDialog() {
        _state.update { current ->
            current.copy(
                visiblePermissionDialogQueue = current.visiblePermissionDialogQueue.drop(1)
            )
        }
    }

    private fun verifyCreds() {
        viewModelScope.launch {
            val token = dataStore.jwtToken.first()

            if (!token.isNullOrEmpty()) {
                authRepository.verifyToken(token).onSuccess {
                    _state.update {
                        it.copy(nextNavigationRoute = Destination.Dash)
                    }
                }.onError {
                    _state.update {
                        it.copy(nextNavigationRoute = Destination.Auth.Login)
                    }
                }
            } else {
                _state.update {
                    it.copy(nextNavigationRoute = Destination.Auth.Login)
                }
            }

            if (_state.value.allPermissionsGranted) {
                goAhead()
            }
        }
    }

    private fun goAhead() {
        val destination = _state.value.nextNavigationRoute
        if (destination != null) {
            viewModelScope.launch {
                _events.send(StartEvent.ContinueNavigation(destination))
            }
        }
    }

    private fun grantAllPermissions() {
        _state.update {
            it.copy(allPermissionsGranted = true)
        }
        if (_state.value.nextNavigationRoute != null) goAhead()
    }
}
