package dev.yuanzix.cyclist.dash.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import dev.yuanzix.cyclist.R
import dev.yuanzix.cyclist.ui.theme.CyclistTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Search") }
                )
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = stringResource(R.string.qr_code_scanner)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    CyclistTheme {
        HomeScreen(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}