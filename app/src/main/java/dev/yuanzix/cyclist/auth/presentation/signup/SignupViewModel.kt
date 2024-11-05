package dev.yuanzix.cyclist.auth.presentation.signup

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

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state = _state

    private val _events = Channel<SignupEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SignupAction) {
        when (action) {
            is SignupAction.NavigateToLogin -> {
                viewModelScope.launch {
                    _events.send(SignupEvent.NavigateToLogin)
                }
            }

            is SignupAction.TogglePasswordVisibility -> {
                _state.value = state.value.copy(hidePassword = !state.value.hidePassword)
            }

            is SignupAction.OnNameChanged -> {
                _state.value = state.value.copy(name = action.name)
            }

            is SignupAction.OnEmailChanged -> {
                _state.value = state.value.copy(email = action.email)
            }

            is SignupAction.OnPasswordChanged -> {
                _state.value = state.value.copy(password = action.password)
            }

            is SignupAction.OnSignupClicked -> {
                viewModelScope.launch {
                    authRepository.signup(
                        name = state.value.name,
                        email = state.value.email,
                        password = state.value.password
                    ).onSuccess {
                        userPreferencesDataStore.saveUserDetails(
                            email = state.value.email,
                            jwtToken = it.token
                        )
                        _events.send(SignupEvent.NavigateToDash)
                    }.onError { error ->
                        _events.send(SignupEvent.Error(error))
                    }
                }
            }
        }
    }
}