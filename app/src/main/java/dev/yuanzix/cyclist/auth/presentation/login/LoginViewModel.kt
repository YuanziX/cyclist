package dev.yuanzix.cyclist.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuanzix.cyclist.auth.domain.AuthRepository
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.domain.util.onError
import dev.yuanzix.cyclist.core.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.NavigateToSignup -> {
                viewModelScope.launch {
                    _events.send(LoginEvent.NavigateToSignup)
                }
            }

            is LoginAction.TogglePasswordVisibility -> {
                _state.value = state.value.copy(hidePassword = !state.value.hidePassword)
            }

            is LoginAction.OnEmailChanged -> {
                _state.value = state.value.copy(email = action.email)
            }

            is LoginAction.OnPasswordChanged -> {
                _state.value = state.value.copy(password = action.password)
            }

            is LoginAction.OnLoginClicked -> {
                viewModelScope.launch {
                    authRepository.login(
                        email = state.value.email, password = state.value.password
                    ).onSuccess {
                        userPreferencesDataStore.saveUserDetails(
                            email = state.value.email, jwtToken = it.token
                        )
                        _events.send(LoginEvent.NavigateToHome)
                    }.onError { error ->
                        _events.send(LoginEvent.Error(error))
                    }
                }
            }
        }
    }
}