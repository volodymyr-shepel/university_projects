package com.rogue.travelguru.ui.presentation.forgot_password.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.rogue.travelguru.components.EmailField
import com.rogue.travelguru.components.SmallSpacer
import com.rogue.travelguru.core.Constants.RESET_PASSWORD_BUTTON
import com.rogue.travelguru.utils.EmailState
import com.rogue.travelguru.utils.EmailStateSaver

@Composable
fun ForgotPasswordContent(
    modifier: Modifier,
    padding: PaddingValues,
    sendPasswordResetEmail: (email: String) -> Unit,
) {
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }

    Column(
        modifier = modifier.padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(emailState = emailState, imeAction = ImeAction.Done, modifier = Modifier.testTag("EmailField"))
        SmallSpacer()
        Button(
            onClick = {
                sendPasswordResetEmail(emailState.text)
            },
            enabled = emailState.isValid,
            modifier = Modifier.fillMaxWidth().testTag("ResetPasswordButton")
        ) {
            Text(
                text = RESET_PASSWORD_BUTTON,
                fontSize = 15.sp
            )
        }
    }
}