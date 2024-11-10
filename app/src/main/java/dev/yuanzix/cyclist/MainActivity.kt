package dev.yuanzix.cyclist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.yuanzix.cyclist.core.navigation.BaseNavigation
import dev.yuanzix.cyclist.ui.theme.CyclistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyclistTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BaseNavigation(
                        shouldShowRequestPermissionRationale = ::shouldShowRequestPermissionRationale,
                        openAppSettings = ::openAppSettings,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}