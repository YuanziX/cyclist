package dev.yuanzix.cyclist.dash.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.yuanzix.cyclist.dash.domain.Bicycle

@Composable
fun BicycleCard(
    bicycle: Bicycle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val width = LocalConfiguration.current.screenWidthDp * 0.45f
    val contentColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier
            .width(width.dp)
            .height(200.dp)
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = bicycle.image,
            contentDescription = null,
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = bicycle.model,
            fontWeight = FontWeight.Light,
            color = contentColor
        )
    }
}
