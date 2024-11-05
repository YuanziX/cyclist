package dev.yuanzix.cyclist.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BaseNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.StartScreen,
    ) {
        startComposable(
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