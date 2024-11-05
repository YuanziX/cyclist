package dev.yuanzix.cyclist.core.navigation

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.yuanzix.cyclist.core.presentation.ObserveAsEvents
import dev.yuanzix.cyclist.core.presentation.toString
import dev.yuanzix.cyclist.dash.presentation.bicycle_details.BicycleDetailsEvent
import dev.yuanzix.cyclist.dash.presentation.bicycle_details.BicycleDetailsScreen
import dev.yuanzix.cyclist.dash.presentation.bicycle_details.BicycleDetailsViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.bicycleDetailsComposable(
    navController: NavController
) {
    composable<Destination.Dash.BicycleDetails> { backStackEntry ->
        val params: Destination.Dash.BicycleDetails = backStackEntry.toRoute()

        val viewModel: BicycleDetailsViewModel = koinInject { parametersOf(params.bikeId) }
        val state = viewModel.state.collectAsStateWithLifecycle()
        val ctx = LocalContext.current

        ObserveAsEvents(
            events = viewModel.events
        ) {
            when (it) {
                is BicycleDetailsEvent.CouldNotFetchBicycleData -> {
                    navController.popBackStack()
                }

                is BicycleDetailsEvent.ShortDurationNotAllowed -> {
                    Toast.makeText(ctx, "Duration shorter than 1Hr not allowed", Toast.LENGTH_SHORT)
                        .show()
                }

                is BicycleDetailsEvent.RentedSuccessfully -> {
                    Toast.makeText(ctx, "Bike rented successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destination.Dash) {
                        popUpTo<Destination.Dash.BicycleDetails> {
                            inclusive = true
                        }
                    }
                }

                is BicycleDetailsEvent.Error -> {
                    Toast.makeText(ctx, it.error.toString(ctx), Toast.LENGTH_SHORT).show()
                }
            }
        }

        BicycleDetailsScreen(
            state = state.value,
            onAction = {
                viewModel.onAction(it)
            }
        )
    }
}