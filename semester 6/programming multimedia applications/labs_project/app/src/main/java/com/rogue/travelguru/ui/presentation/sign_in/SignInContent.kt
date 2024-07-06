package com.rogue.travelguru.ui.presentation.sign_in

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rogue.travelguru.R
import com.rogue.travelguru.components.EmailField
import com.rogue.travelguru.components.PasswordField
import com.rogue.travelguru.core.Constants.FORGOT_PASSWORD
import com.rogue.travelguru.core.Constants.NO_ACCOUNT
import com.rogue.travelguru.core.Constants.VERTICAL_DIVIDER
import com.rogue.travelguru.model.AuthState
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.theme.stronglyDeemphasizedAlpha
import com.rogue.travelguru.utils.EmailState
import com.rogue.travelguru.utils.PasswordState

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    contentPadding : PaddingValues,
    signInWithEmailAndPassword: (email: String, password: String) -> Unit,
    oneTapSignIn : () -> Unit,
    signInAnonymously : () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {

    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        val passwordFocusRequest = remember { FocusRequester() }
        val emailState = remember { EmailState() }
        EmailField(emailState, onImeAction = { passwordFocusRequest.requestFocus() })
        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember { PasswordState() }
        PasswordField(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { signInWithEmailAndPassword(emailState.text, passwordState.text) },
            modifier = Modifier.focusRequester(passwordFocusRequest)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { signInWithEmailAndPassword(emailState.text, passwordState.text) },
            modifier = Modifier.fillMaxWidth(),
            enabled = emailState.isValid &&
                    passwordState.isValid
        ) {
            Text(text = stringResource(id = R.string.sign_in))
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (DataProvider.authState == AuthState.SignedOut) {
            OrSignInAsGuest(
                onSignInAsGuest = signInAnonymously,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                oneTapSignIn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.sign_in_with_google),
                modifier = Modifier.padding(6.dp),
                color = Color.Black.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.clickable {
                    navigateToForgotPasswordScreen()
                },
                text = FORGOT_PASSWORD,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = VERTICAL_DIVIDER,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                },
                text = NO_ACCOUNT,
                fontSize = 15.sp
            )
        }
    }
}
@Composable
fun OrSignInAsGuest(
    onSignInAsGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
            modifier = Modifier.paddingFromBaseline(top = 25.dp)
        )
        OutlinedButton(
            onClick = onSignInAsGuest,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 10.dp),
        ) {
            Text(text = stringResource(id = R.string.sign_in_guest))
        }
    }
}



