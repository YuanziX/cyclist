package dev.yuanzix.cyclist.core.navigation

import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.yuanzix.cyclist.auth.presentation.login.LoginEvent
import dev.yuanzix.cyclist.auth.presentation.login.LoginScreen
import dev.yuanzix.cyclist.auth.presentation.login.LoginViewModel
import dev.yuanzix.cyclist.auth.presentation.signup.SignupEvent
import dev.yuanzix.cyclist.auth.presentation.signup.SignupScreen
import dev.yuanzix.cyclist.auth.presentation.signup.SignupViewModel
import dev.yuanzix.cyclist.core.presentation.ObserveAsEvents
import dev.yuanzix.cyclist.core.presentation.toString
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.loginComposable(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable<Destination.Auth.Login> {
        val viewModel: LoginViewModel = koinViewModel()
        val ctx = LocalContext.current
        val state = viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(events = viewModel.events, onEvent = { event ->
            when (event) {
                is LoginEvent.NavigateToDash -> {
                    navController.navigate(Destination.Dash)
                }

                is LoginEvent.NavigateToSignup -> {
                    navController.navigate(Destination.Auth.Signup)
                }

                is LoginEvent.Error -> {
                    Toast.makeText(ctx, event.message.toString(ctx), Toast.LENGTH_SHORT).show()
                }
            }
        })

        LoginScreen(
            state = state.value, onAction = { action ->
                viewModel.onAction(action)
            }, modifier = modifier
        )
    }
}

fun NavGraphBuilder.signupComposable(
    navController: NavController, modifier: Modifier = Modifier
) {
    composable<Destination.Auth.Signup> {
        val viewModel: SignupViewModel = koinViewModel()
        val ctx = LocalContext.current
        val state = viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(events = viewModel.events, onEvent = { event ->
            when (event) {
                is SignupEvent.NavigateToDash -> {
                    navController.navigate(Destination.Dash)
                }

                is SignupEvent.NavigateToLogin -> {
                    navController.navigate(Destination.Auth.Login)
                }

                is SignupEvent.Error -> {
                    Toast.makeText(ctx, event.message.toString(ctx), Toast.LENGTH_SHORT).show()
                }
            }
        })

        SignupScreen(
            state = state.value, onAction = { action ->
                viewModel.onAction(action)
            }, modifier = modifier
        )
    }
}