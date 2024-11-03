package dev.yuanzix.cyclist.core.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object StartScreen: Destination

    @Serializable
    object Auth {
        @Serializable
        data object Login: Destination

        @Serializable
        data object Signup: Destination
    }

    @Serializable
    object Dash {
        @Serializable
        data object Home: Destination

        @Serializable
        data object Profile: Destination
    }
}