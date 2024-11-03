package dev.yuanzix.cyclist.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yuanzix.cyclist.R
import dev.yuanzix.cyclist.auth.presentation.components.PasswordTextField
import dev.yuanzix.cyclist.ui.theme.CyclistTheme

@Composable
fun LoginScreen(
    state: LoginState, onAction: (LoginAction) -> Unit, modifier: Modifier = Modifier
) {
    val contentColor = MaterialTheme.colorScheme.onBackground

    if (state.isLoading) {
        return Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.welcome_message),
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
            Image(
                painter = painterResource(id = R.drawable.bicycle),
                contentDescription = "Bicycle Image",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.email,
                onValueChange = { onAction(LoginAction.OnEmailChanged(it)) },
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(4.dp))
            PasswordTextField(
                value = state.password,
                onValueChange = { onAction(LoginAction.OnPasswordChanged(it)) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                hidePassword = state.hidePassword,
                togglePasswordVisibility = { onAction(LoginAction.TogglePasswordVisibility) }
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    onAction(LoginAction.OnLoginClicked)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
            Spacer(Modifier.height(4.dp))
            TextButton(
                onClick = {
                    onAction(LoginAction.NavigateToSignup)
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(R.string.go_to_signup))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    CyclistTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}