package com.rogue.travelguru.ui.presentation.verify_email

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rogue.travelguru.authLogin.AuthViewModel
import com.rogue.travelguru.core.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.presentation.verify_email.components.ReloadUser
import com.rogue.travelguru.ui.presentation.verify_email.components.VerifyEmailContent
import com.rogue.travelguru.utils.Utils.Companion.showMessage

@Composable
fun VerifyEmailScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    authViewModel.reloadUser()
                },
                signOut = {
                    authViewModel.signOut()
                }
            )
        }
    )

    ReloadUser(
        navigateToProfileScreen = {
            if (DataProvider.user?.isEmailVerified == true) {
                navigateToProfileScreen()
            } else {
                showMessage(context, EMAIL_NOT_VERIFIED_MESSAGE)
            }
        }
    )

}