package dev.yuanzix.cyclist.core.presentation.start

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

@Composable
fun StartScreen(
    shouldShowRequestPermissionRationale: KFunction1<String, Boolean>,
    openAppSettings: KFunction0<Unit>,
    state: StartState,
    onAction: (StartAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            wifiPermissions.forEach { permission ->
                onAction(
                    StartAction.OnPermissionResult(
                        permission = permission,
                        isGranted = perms[permission] == true
                    )
                )
            }
        }
    )

    LaunchedEffect(true) {
        permissionLauncher.launch(wifiPermissions)
    }

    // Show permission dialogs if there are permissions to request
    if (state.visiblePermissionDialogQueue.isNotEmpty()) {
        state.visiblePermissionDialogQueue.reversed().forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.NEARBY_WIFI_DEVICES -> WifiPermissionTextProvider()

                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(permission),
                onDismiss = { onAction(StartAction.DismissPermissionDialog) },
                onOkClick = {
                    onAction(StartAction.DismissPermissionDialog)
                    permissionLauncher.launch(wifiPermissions)
                },
                onGoToAppSettingsClick = {
                    onAction(StartAction.DismissPermissionDialog)
                    openAppSettings()
                }
            )
        }
    } else {
        // Only call this once when all permissions are granted and dialog queue is empty
        onAction(StartAction.OnAllPermissionsGranted)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
