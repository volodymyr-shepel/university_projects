package com.rogue.travelguru.ui.presentation.sign_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rogue.travelguru.R
import com.rogue.travelguru.components.EmailField
import com.rogue.travelguru.components.PasswordField
import com.rogue.travelguru.core.Constants.EMPTY_STRING
import com.rogue.travelguru.ui.theme.stronglyDeemphasizedAlpha
import com.rogue.travelguru.utils.ConfirmPasswordState
import com.rogue.travelguru.utils.EmailState
import com.rogue.travelguru.utils.PasswordState
import androidx.compose.ui.platform.testTag

@Composable
fun SignUpContent(
    modifier: Modifier,
    onSignUpSubmitted: (email: String, password: String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }
        val emailState = remember { EmailState(EMPTY_STRING) }
        EmailField(
            emailState = emailState,
            onImeAction = { passwordFocusRequest.requestFocus() },
            modifier = Modifier.testTag("EmailField")
        )

        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember { PasswordState() }
        PasswordField(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequest).testTag("PasswordField")
        )

        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
        PasswordField(
            label = stringResource(id = R.string.confirm_password),
            passwordState = confirmPasswordState,
            onImeAction = { onSignUpSubmitted(emailState.text, passwordState.text) },
            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest).testTag("ConfirmPasswordField")
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.terms_and_conditions),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSignUpSubmitted(emailState.text, passwordState.text) },
            modifier = Modifier.fillMaxWidth().testTag("SignUpButton"),
            enabled = emailState.isValid &&
                    passwordState.isValid && confirmPasswordState.isValid
        ) {
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}
