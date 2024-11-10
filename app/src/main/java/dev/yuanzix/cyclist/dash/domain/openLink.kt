package dev.yuanzix.cyclist.dash.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import dev.yuanzix.cyclist.BuildConfig

fun Context.openNavigationLink(latitude: String, longitude: String) {
    val browserIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse("${BuildConfig.GOOGLE_MAPS_URL}$latitude,$longitude"))
    startActivity(browserIntent)
}