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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
@Preview(showBackground = true)
fun RooydadLogin() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Hey \uD83E\uDD1D\nLogin Now!",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = {}) {

                    Text(
                        text = "I Am An Old User",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                    )
                }
                Text(text = " / ")
                TextButton(onClick = {}) {
                    Text(
                        text = "Create New",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            var text by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                placeholder = { Text(text = "UserName") },
                shape = RoundedCornerShape(size = 10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(size = 10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = {}) {

                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                    )
                }
                Text(text = " / ")
                TextButton(onClick = {}) {
                    Text(
                        text = "Reset",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Google Sign In")
            }
        }


    }
}