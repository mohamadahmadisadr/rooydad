package com.rooydad.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
@Preview(showBackground = true)
fun RooydadLogin(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Hey \uD83E\uDD1D\n ${if (viewModel.uiModeState.value == UiMode.Login) "Login" else "Register"} Now!",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = {
                    viewModel::changeUiMode
                }, enabled = viewModel.uiModeState.value == UiMode.Register) {

                    Text(
                        text = "I Am An Old User",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp),
                    )
                }
                Text(text = " / ")
                TextButton(
                    onClick = {
                        viewModel::changeUiMode
                    },
                    enabled = viewModel.uiModeState.value == UiMode.Login,
                ) {
                    Text(
                        text = "Create New",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.authModel.value.username,
                onValueChange = { viewModel::setUserName },
                placeholder = { Text(text = "UserName") },
                shape = RoundedCornerShape(size = 15.dp),
                isError = viewModel.authModel.value.usernameError != null,
                supportingText = { Text(text = viewModel.authModel.value.usernameError ?: "") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.authModel.value.password,
                onValueChange = viewModel.setPassword(),
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(size = 15.dp),
                isError = viewModel.authModel.value.passwordError != null,
                supportingText = { Text(text = viewModel.authModel.value.passwordError ?: "") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                ),
            )


            TextButton(onClick = viewModel.onResetPasswordClick()) {

                Text(
                    text = "Forgot Password?",
                    style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
            Button(
                onClick = viewModel.onSignInOrSignUpClick(),
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.uiState.value == UiState.Idle
            ) {
                if (viewModel.uiState.value == UiState.Idle)
                    Text(text = if (viewModel.uiModeState.value == UiMode.Login) "Login" else "Register")
                else CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(5.dp))



            OutlinedButton(
                onClick = viewModel.onSignInWithGoogleClick(
                    context,
                    stringResource(R.string.client_id)
                ), modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.uiState.value == UiState.Idle
            ) {
                Text(text = "Google Sign In")
            }
        }


    }
}

