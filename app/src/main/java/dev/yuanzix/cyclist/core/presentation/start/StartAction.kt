package dev.yuanzix.cyclist.core.presentation.start

sealed interface StartAction {
    data class OnPermissionResult(val permission: String, val isGranted: Boolean) : StartAction
    data object DismissPermissionDialog : StartAction
    data object OnAllPermissionsGranted : StartAction
}