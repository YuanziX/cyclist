package dev.yuanzix.cyclist.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.yuanzix.cyclist.core.presentation.ObserveAsEvents
import dev.yuanzix.cyclist.core.presentation.start.StartEvent
import dev.yuanzix.cyclist.core.presentation.start.StartScreen
import dev.yuanzix.cyclist.core.presentation.start.StartViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.startComposable(
    navController: NavController
) {
    composable<Destination.StartScreen> {
        val viewModel: StartViewModel = koinViewModel()

        ObserveAsEvents(events = viewModel.events, onEvent = { event ->
            when (event) {
                StartEvent.NavigateToLogin -> {
                    navController.navigate(Destination.Auth.Login)
                }

                is StartEvent.NavigateToDash -> {
                    navController.navigate(Destination.Dash)
                }
            }
        })

        StartScreen()
    }
}