package dev.yuanzix.cyclist.auth.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.yuanzix.cyclist.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hidePassword: Boolean = true,
    togglePasswordVisibility: () -> Unit
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.CenterEnd
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None
        )
        IconButton(onClick = togglePasswordVisibility) {
            Icon(
                imageVector = if (hidePassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = stringResource(R.string.show_hide_password)
            )
        }
    }
}