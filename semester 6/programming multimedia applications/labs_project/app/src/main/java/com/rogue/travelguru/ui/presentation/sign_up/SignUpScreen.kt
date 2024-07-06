package com.rogue.travelguru.ui.presentation.sign_up

import android.util.Log
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
import com.rogue.travelguru.R
import com.rogue.travelguru.components.AlertDialog
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBack
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.presentation.sign_up.components.SendEmailVerification
import com.rogue.travelguru.ui.presentation.sign_up.components.SignUp
import com.rogue.travelguru.ui.presentation.sign_up.components.SignUpContent


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit

){
    Scaffold(
        topBar = {
            TGTopAppBarWithArrowBack(
                topAppBarText = stringResource(id = R.string.create_account),
                onNavUp = navigateBack,
            )
        },
        content = { contentPadding ->
            SignUpContent(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                onSignUpSubmitted = { email, password ->
                    viewModel.signUpWithEmailAndPassword(email, password)
                },
                contentPadding = contentPadding
            )
        }
    )

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        }

    )
    SendEmailVerification()

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