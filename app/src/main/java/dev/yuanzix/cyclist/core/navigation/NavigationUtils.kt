package dev.yuanzix.cyclist.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object StartScreen : Destination

    @Serializable
    data object Auth : Destination {
        @Serializable
        data object Login : Destination

        @Serializable
        data object Signup : Destination
    }

    @Serializable
    data object Dash : Destination {
        @Serializable
        data object Home : Destination

        @Serializable
        data class BicycleDetails(
            val bikeId: String
        ) : Destination

        @Serializable
        data object MyCycle : Destination
    }
}

data class NavItem(
    val name: String,
    val route: Destination,
    val icon: ImageVector
)