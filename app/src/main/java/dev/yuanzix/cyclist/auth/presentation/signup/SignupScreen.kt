package dev.yuanzix.cyclist.auth.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yuanzix.cyclist.R
import dev.yuanzix.cyclist.auth.presentation.components.PasswordTextField
import dev.yuanzix.cyclist.ui.theme.CyclistTheme

@Composable
fun SignupScreen(
    state: SignupState,
    onAction: (SignupAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.signup_message),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = contentColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.name,
                onValueChange = {
                    onAction(SignupAction.OnNameChanged(it))
                },
                label = { Text(text = stringResource(id = R.string.name)) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(4.dp))
            TextField(
                value = state.email,
                onValueChange = {
                    onAction(SignupAction.OnEmailChanged(it))
                },
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(4.dp))
            PasswordTextField(
                value = state.password,
                onValueChange = {
                    onAction(SignupAction.OnPasswordChanged(it))
                },
                hidePassword = state.hidePassword,
                togglePasswordVisibility = {
                    onAction(SignupAction.TogglePasswordVisibility)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    onAction(SignupAction.OnSignupClicked)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(text = stringResource(R.string.signup))
            }
            Spacer(Modifier.height(4.dp))
            TextButton(
                onClick = {
                    onAction(SignupAction.NavigateToLogin)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(R.string.go_to_login))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SignupScreenPreview(modifier: Modifier = Modifier) {
    CyclistTheme {
        SignupScreen(
            state = SignupState(),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}