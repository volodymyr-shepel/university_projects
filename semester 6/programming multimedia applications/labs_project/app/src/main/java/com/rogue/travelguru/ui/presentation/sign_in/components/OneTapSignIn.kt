package com.rogue.travelguru.ui.presentation.sign_in.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response

@Composable
fun OneTapSignIn(
    launch: (result: BeginSignInResult) -> Unit
) {
    when(val oneTapSignInResponse = DataProvider.oneTapSignInResponse) {
        is Response.Loading ->  {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
            LaunchedEffect(signInResult) {
                launch(signInResult)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:OneTap", "${oneTapSignInResponse.e}")
            DataProvider.openAlertDialog = oneTapSignInResponse.e.message.toString()
            DataProvider.oneTapSignInResponse = Response.Success(null)
        }
    }
}