package com.rogue.travelguru.ui.presentation.sign_up.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator

@Composable
fun SignUp(
    sendEmailVerification: () -> Unit,
) {
    when(val signUpResponse = DataProvider.emailPasswordSignUpResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data ?: false
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    sendEmailVerification()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                Log.i("ERROR WHEN SIGNING UP", "${signUpResponse.e}")
                DataProvider.openAlertDialog = signUpResponse.e.message.toString()
                DataProvider.emailPasswordSignUpResponse = Response.Success(null)
            }
        }
    }
}