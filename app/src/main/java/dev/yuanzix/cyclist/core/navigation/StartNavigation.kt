package dev.yuanzix.cyclist.core.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.yuanzix.cyclist.core.presentation.ObserveAsEvents
import dev.yuanzix.cyclist.core.presentation.start.StartEvent
import dev.yuanzix.cyclist.core.presentation.start.StartScreen
import dev.yuanzix.cyclist.core.presentation.start.StartViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

fun NavGraphBuilder.startComposable(
    shouldShowRequestPermissionRationale: KFunction1<String, Boolean>,
    openAppSettings: KFunction0<Unit>,
    navController: NavController
) {
    composable<Destination.StartScreen> {
        val viewModel: StartViewModel = koinViewModel()

        ObserveAsEvents(events = viewModel.events, onEvent = { event ->
            when (event) {
                is StartEvent.ContinueNavigation -> {
                    navController.navigate(event.destination)
                }
            }
        })

        StartScreen(
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale,
            openAppSettings = openAppSettings,
            state = viewModel.state.collectAsStateWithLifecycle().value,
            onAction = { viewModel.onAction(it) },
        )
    }
}