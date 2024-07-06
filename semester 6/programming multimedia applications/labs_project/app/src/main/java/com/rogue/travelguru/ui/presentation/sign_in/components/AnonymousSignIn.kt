package com.rogue.travelguru.ui.presentation.sign_in.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response

@Composable
fun AnonymousSignIn() {
    when (val anonymousResponse = DataProvider.anonymousSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:AnonymousSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> anonymousResponse.data?.let { authResult ->
            Log.i("Login:AnonymousSignIn", "Success: $authResult")
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:AnonymousSignIn", "${anonymousResponse.e}")
            DataProvider.openAlertDialog = anonymousResponse.e.message.toString()
            DataProvider.anonymousSignInResponse = Response.Success(null)

        }
    }
}