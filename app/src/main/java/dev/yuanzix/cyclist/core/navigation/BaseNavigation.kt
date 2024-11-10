package dev.yuanzix.cyclist.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

@Composable
fun BaseNavigation(
    shouldShowRequestPermissionRationale: KFunction1<String, Boolean>,
    openAppSettings: KFunction0<Unit>,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.StartScreen,
    ) {
        startComposable(
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale,
            openAppSettings = openAppSettings,
            navController = navController
        )

        loginComposable(
            modifier = modifier, navController = navController
        )

        signupComposable(
            modifier = modifier, navController = navController
        )

        composable<Destination.Dash> {
            DashboardNavigation(
                modifier = modifier, navController = navController
            )
        }

        bicycleDetailsComposable(
            navController = navController
        )
    }
}