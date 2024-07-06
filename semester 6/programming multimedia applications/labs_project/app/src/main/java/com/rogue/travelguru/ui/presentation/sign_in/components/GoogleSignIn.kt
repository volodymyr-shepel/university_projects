package com.rogue.travelguru.ui.presentation.sign_in.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response

@Composable
fun GoogleSignIn(

) {
    when (val signInWithGoogleResponse = DataProvider.googleSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:GoogleSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
            DataProvider.openAlertDialog = signInWithGoogleResponse.e.message.toString()
            DataProvider.googleSignInResponse = Response.Success(null)
        }
    }
}