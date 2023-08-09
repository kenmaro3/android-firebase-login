package com.example.firebaselogin.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebaselogin.ui.theme.FirebaseLoginTheme

@Composable
fun Home(name: String, logoutCallback: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 42.dp, end = 32.dp, top = 32.dp)
    ){

        Column(){
            Text(
                text = "Hello $name",
            )

            Button(
                onClick = {
                    logoutCallback()
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("log out")
            }
        }
    }
}

