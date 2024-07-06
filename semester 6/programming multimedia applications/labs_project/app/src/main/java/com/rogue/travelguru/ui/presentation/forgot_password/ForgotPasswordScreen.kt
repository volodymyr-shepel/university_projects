package com.rogue.travelguru.ui.presentation.forgot_password

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rogue.travelguru.R
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBack
import com.rogue.travelguru.core.Constants.RESET_PASSWORD_MESSAGE
import com.rogue.travelguru.ui.presentation.forgot_password.components.ForgotPassword
import com.rogue.travelguru.ui.presentation.forgot_password.components.ForgotPasswordContent
import com.rogue.travelguru.utils.Utils.Companion.showMessage

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TGTopAppBarWithArrowBack(
                topAppBarText = stringResource(id = R.string.forgot_password),
                onNavUp = navigateBack,
            )
        },
        content = { padding ->
            ForgotPasswordContent(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                padding = padding,
                sendPasswordResetEmail = { email ->
                    viewModel.sendPasswordResetEmail(email)
                }
            )
        }
    )

    ForgotPassword(
        navigateBack = navigateBack,
        showResetPasswordMessage = {
            showMessage(context, RESET_PASSWORD_MESSAGE)
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)

        }
    )
}