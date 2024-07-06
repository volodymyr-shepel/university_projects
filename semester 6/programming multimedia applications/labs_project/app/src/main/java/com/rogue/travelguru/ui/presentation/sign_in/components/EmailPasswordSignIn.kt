package com.rogue.travelguru.ui.presentation.sign_in.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response

@Composable
fun  EmailPasswordSignIn (){
    when (val emailPasswordResponse = DataProvider.emailPasswordSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:Email Password", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> emailPasswordResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:GoogleSignIn", "${emailPasswordResponse.e}")
            DataProvider.openAlertDialog = emailPasswordResponse.e.message.toString()
            DataProvider.emailPasswordSignInResponse = Response.Success(null)
        }
    }

}