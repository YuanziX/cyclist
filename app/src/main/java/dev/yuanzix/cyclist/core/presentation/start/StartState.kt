package dev.yuanzix.cyclist.core.presentation.start

import dev.yuanzix.cyclist.core.navigation.Destination

data class StartState(
    val visiblePermissionDialogQueue: List<String> = emptyList(),
    val allPermissionsGranted: Boolean = false,
    val nextNavigationRoute: Destination? = null,
)
