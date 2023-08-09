package com.example.firebaselogin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaselogin.Util.GoogleAuthUiClient
import com.example.firebaselogin.View.Home
import com.example.firebaselogin.View.SignInScreen
import com.example.firebaselogin.ViewModel.SignInViewModel
import com.example.firebaselogin.ui.theme.FirebaseLoginTheme
import com.google.android.gms.auth.api.identity.Identity
import com.example.firebaselogin.ui.theme.FirebaseLoginTheme

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FirebaseLoginTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("home"){
                            val sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
                            val email = sharedPref.getString("email", "")
                            Home(
                                name = email!!,
                                logoutCallback = {

                                    lifecycleScope.launch {
                                        val signInResult =
                                            googleAuthUiClient.signOut(
                                            )
                                        Toast.makeText(
                                            applicationContext,
                                            "logged out",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("sign_in")
                                    }
                                }
                            )
                        }
                        composable("sign_in") {

                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                Log.d("DEBUG", "launchedEffect, signin")

                                val sharedPref = getSharedPreferences("preferences", MODE_PRIVATE)
                                val logStatus = sharedPref.getBoolean("log_status", false)

                                Log.d("DEBUG", "mainactivity0====")
                                Log.d("DEBUG", logStatus.toString())
                                Log.d("DEBUG", googleAuthUiClient.getSignedInUser().toString())
                                if (googleAuthUiClient.getSignedInUser() != null) {

                                    Log.d("DEBUG", "mainactivity1====")
                                    navController.navigate("home")

                                    val sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                                    val editor = sharedPref.edit()
                                    editor.putBoolean("log_status", true)
                                    editor.apply()
                                }else{

                                    Log.d("DEBUG", "mainactivity2====")
                                }
                            }

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "logged in",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val currentUser = googleAuthUiClient.getSignedInUser()

                                    val sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                                    val editor = sharedPref.edit()
                                    editor.putBoolean("log_status", true)
                                    editor.putString("email", currentUser?.email)
                                    editor.apply()
                                    navController.navigate("home")
                                }else{

                                }

                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult =
                                                googleAuthUiClient.signInWithIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    Log.d("DEBUG", "here")
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )

                        }

                    }
                }

            }


        }
    }

}