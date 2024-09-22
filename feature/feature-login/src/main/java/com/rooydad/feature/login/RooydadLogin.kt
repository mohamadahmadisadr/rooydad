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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay


@Composable
@Preview(showBackground = true)
fun RooydadLogin(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Hey \uD83E\uDD1D\n ${if (uiState == UiMode.Login) "Login" else "Register"} Now!",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = {
                    viewModel.changeUiMode()
                }, enabled = uiState.uiMode == UiMode.Register) {

                    Text(
                        text = "I Am An Old User",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp),
                    )
                }
                Text(text = " / ")
                TextButton(
                    onClick = { viewModel.changeUiMode() },
                    enabled = uiState.uiMode == UiMode.Login,
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
                value = uiState.authModel.username,
                onValueChange = viewModel.setUserName(),
                placeholder = { Text(text = "UserName") },
                shape = RoundedCornerShape(size = 15.dp),
                isError = uiState.authModel.usernameError != null,
                supportingText = {
                    Text(
                        text = uiState.authModel.usernameError ?: ""
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            var passwordVisibility = remember { mutableStateOf(false) }

            LaunchedEffect(key1 = passwordVisibility.value) {
                if (passwordVisibility.value) {
                    delay(3_000)
                    passwordVisibility.value = !passwordVisibility.value
                }
            }


            OutlinedTextField(
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                value = uiState.authModel.password,
                onValueChange = viewModel.setPassword(),
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(size = 15.dp),
                isError = uiState.authModel.passwordError != null,
                trailingIcon = {
                    TextButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Text(text = if (passwordVisibility.value) "Hide" else "Show")
                    }
                },
                supportingText = {
                    Text(
                        text = uiState.authModel.passwordError ?: ""
                    )
                },
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
                enabled = uiState.uiState == UiState.Idle
            ) {
                if (uiState.uiState == UiState.Idle) Text(text = if (uiState.uiMode == UiMode.Login) "Login" else "Register")
                else CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(5.dp))



            OutlinedButton(
                onClick = viewModel.onSignInWithGoogleClick(
                    context, stringResource(R.string.client_id)
                ), modifier = Modifier.fillMaxWidth(), enabled = uiState.uiState == UiState.Idle
            ) {
                Text(text = "Google Sign In")
            }
        }


    }
}

