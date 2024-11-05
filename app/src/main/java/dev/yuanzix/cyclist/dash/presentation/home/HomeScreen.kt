package dev.yuanzix.cyclist.dash.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.yuanzix.cyclist.R
import dev.yuanzix.cyclist.dash.presentation.home.components.BicycleCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    state: HomeState, onAction: (HomeAction) -> Unit, modifier: Modifier = Modifier
) {
    val contentColor = MaterialTheme.colorScheme.onSurface

    if (state.isLoading) {
        return Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            TextField(
                value = "", onValueChange = {}, colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ), label = { Text("Search") }, modifier = Modifier.fillMaxWidth()
            )
            IconButton(onClick = { TODO() }) {
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = stringResource(R.string.qr_code_scanner),
                    tint = contentColor
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        ContextualFlowRow(
            itemCount = state.bicycles.size,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            state.bicycles.forEach { bike ->
                BicycleCard(bicycle = bike,
                    onClick = { onAction(HomeAction.NavigateToCycleWithID(bike.id)) })
            }
        }
    }
}
