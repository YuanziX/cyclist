package dev.yuanzix.cyclist.dash.presentation.mycycle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.yuanzix.cyclist.R

@Composable
fun MyCycleScreen(
    state: MyCycleState,
    onAction: (MyCycleAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        return Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    if (state.bicycle == null) {
        return Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "You don't have any rented bikes at the moment !",
                fontSize = 20.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
            )
        }
    }

    return Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = state.bicycle.image,
            contentDescription = state.bicycle.model,
            modifier = Modifier.height(350.dp)
        )
        Spacer(Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.rented_cycle_greeting),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(24.dp))
        FloatingActionButton(
            onClick = {
                onAction(MyCycleAction.LockUnlockCycle)
            }
        ) {
            Text(
                text = stringResource(R.string.unlock_cycle),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)
            )
        }
    }
}