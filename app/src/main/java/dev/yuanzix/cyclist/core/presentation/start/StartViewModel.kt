package dev.yuanzix.cyclist.core.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.auth.domain.AuthRepository
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class StartViewModel(
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _events = Channel<StartEvent>()
    val events = _events.receiveAsFlow()

    init {
        verifyCreds()
    }

    private fun verifyCreds() {
        viewModelScope.launch {
            val token = dataStore.jwtToken.first()

            if (!token.isNullOrEmpty()) {
                authRepository.verifyToken(token).onSuccess {
                    _events.send(StartEvent.NavigateToDash)
                }.onError {
                    _events.send(StartEvent.NavigateToLogin)
                }
            } else {
                _events.send(StartEvent.NavigateToLogin)
            }
        }
    }
}
