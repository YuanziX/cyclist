package dev.yuanzix.cyclist.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.yuanzix.cyclist.dash.presentation.home.HomeScreen

fun NavGraphBuilder.homeComposable() {
    composable<Destination.Dash.Home> {
        HomeScreen()
    }
}