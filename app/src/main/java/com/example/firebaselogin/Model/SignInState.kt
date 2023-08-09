package com.example.firebaselogin.Model

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)