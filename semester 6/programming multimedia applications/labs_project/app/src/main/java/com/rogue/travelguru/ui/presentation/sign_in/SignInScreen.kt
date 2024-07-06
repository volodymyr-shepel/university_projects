package com.rogue.travelguru.ui.presentation.sign_in

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.rogue.travelguru.R
import com.rogue.travelguru.authLogin.AuthViewModel
import com.rogue.travelguru.components.AlertDialog
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBar
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.presentation.sign_in.components.AnonymousSignIn
import com.rogue.travelguru.ui.presentation.sign_in.components.EmailPasswordSignIn
import com.rogue.travelguru.ui.presentation.sign_in.components.GoogleSignIn
import com.rogue.travelguru.ui.presentation.sign_in.components.OneTapSignIn

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                authViewModel.signInWithGoogle(credentials)
            }
            catch (e: ApiException) {
                Log.e("SignInScreen:Launcher","Login One-tap $e")
            }
        }
        else if (result.resultCode == Activity.RESULT_CANCELED){
            Log.e("SignInScreen:Launcher","OneTapClient Canceled")
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }
        Scaffold(
            topBar = {
                TGTopAppBar(
                    topAppBarText = stringResource(id = R.string.sign_in),
                )
            },

            content = { contentPadding ->
                SignInContent(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    contentPadding = contentPadding,
                    signInWithEmailAndPassword = { email : String, password : String ->
                        authViewModel.signInWithEmailAndPassword(email, password)
                    },
                    navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                    navigateToSignUpScreen = navigateToSignUpScreen,
                    oneTapSignIn = { authViewModel.oneTapSignIn() },
                    signInAnonymously = {authViewModel.signInAnonymously()}
                )
            }
    )


    GoogleSignIn()
    OneTapSignIn (
        launch = {
            launch(it)
        }
    )
    AnonymousSignIn()

    EmailPasswordSignIn()

    if(DataProvider.openAlertDialog != ""){
        AlertDialog(
            onDismissRequest = { DataProvider.openAlertDialog = "" },
            onConfirmation = {
                DataProvider.openAlertDialog = ""
                Log.i("Value", "New val" + DataProvider.openAlertDialog)
            },
            dialogTitle = "ERROR OCCURRED",
            dialogText = DataProvider.openAlertDialog,
            icon = Icons.Default.ErrorOutline
        )
    }

}
