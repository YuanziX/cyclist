package dev.yuanzix.cyclist.dash.presentation.bicycle_details

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import dev.yuanzix.cyclist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BicycleDetailsScreen(
    state: BicycleDetailsState,
    onAction: (BicycleDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading || state.bicycle == null) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = state.bicycle.model) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .scrollable(
                    rememberScrollState(),
                    orientation = Orientation.Vertical
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = state.bicycle.image,
                contentDescription = state.bicycle.model,
                modifier = Modifier.height(200.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.price_per_day),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "${state.bicycle.pricePerDay} INR",
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.price_per_hour),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "${state.bicycle.pricePerHour} INR",
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(text = stringResource(R.string.select_start_time))
            WheelDateTimePicker { snappedDateTime ->
                onAction(BicycleDetailsAction.SetStartTime(snappedDateTime))
            }
            Spacer(Modifier.height(8.dp))
            Text(text = stringResource(R.string.select_end_time))
            WheelDateTimePicker { snappedDateTime ->
                onAction(BicycleDetailsAction.SetEndTime(snappedDateTime))
            }

            Spacer(
                Modifier.height(24.dp)
            )
            Button(
                onClick = {
                    onAction(BicycleDetailsAction.RentBike)
                }
            ) {
                Text(
                    text = stringResource(R.string.rent_bike),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}