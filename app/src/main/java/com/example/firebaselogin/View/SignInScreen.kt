package com.example.firebaselogin.View

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaselogin.Model.SignInState
import com.example.firebaselogin.R
import com.example.firebaselogin.ui.theme.FirebaseLoginTheme

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start=42.dp, end=32.dp, top=32.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ){

            Column(
                horizontalAlignment = Alignment.Start
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo_test),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(120.dp),
                    alignment = Alignment.CenterStart,

                    )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Login to proceed",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(38.dp))
            }

//            Column(){
//                CompleteDialogContent(
//                    finishCallback = {}
//                )
//            }

            Spacer(modifier = Modifier.height(38.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "sign in with google" ,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))


                Row(){
                    Button(
                        onClick = onSignInClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.White,
                            disabledContentColor = Color.LightGray
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "familiar_face_and_zone_48px",
                                contentScale = ContentScale.Fit,
                                //colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .padding(end = 10.dp)
                                    .size(24.dp)
                            )
                            Text(text = "sign in with google")
                        }
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    val tmp = {}

    FirebaseLoginTheme {
        SignInScreen(
            state = SignInState(isSignInSuccessful = false),
            onSignInClick = {}

        )

    }
}