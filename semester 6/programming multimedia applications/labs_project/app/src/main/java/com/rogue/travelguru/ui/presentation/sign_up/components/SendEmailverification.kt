package com.rogue.travelguru.ui.presentation.sign_up.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator
import com.rogue.travelguru.ui.presentation.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
) {
    when(val sendEmailVerificationResponse = DataProvider.emailVerificationResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> Unit
        is Response.Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                Log.i("ERROR WHEN SENDING EMAIL", "${sendEmailVerificationResponse.e}")
                DataProvider.openAlertDialog = sendEmailVerificationResponse.e.message.toString()
                DataProvider.emailVerificationResponse = Response.Success(null)
            }
        }
    }
}