package com.rogue.travelguru.ui.presentation.forgot_password.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator

@Composable
fun ForgotPassword(
    navigateBack: () -> Unit,
    showResetPasswordMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val sendPasswordResetEmailResponse = DataProvider.sendPasswordResetEmailResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val isPasswordResetEmailSent = sendPasswordResetEmailResponse.data ?: false
            LaunchedEffect(isPasswordResetEmailSent) {
                if (isPasswordResetEmailSent) {
                    navigateBack()
                    showResetPasswordMessage()
                }
            }
        }
        is Response.Failure -> sendPasswordResetEmailResponse.apply {
            LaunchedEffect(e) {
                showErrorMessage(e.message)
                DataProvider.openAlertDialog = sendPasswordResetEmailResponse.e.message.toString()
                DataProvider.sendPasswordResetEmailResponse = Response.Success(null)

            }
        }
    }
}