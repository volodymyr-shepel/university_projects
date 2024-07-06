package com.rogue.travelguru.ui.presentation.verify_email.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rogue.travelguru.components.SmallSpacer
import com.rogue.travelguru.core.Constants.ALREADY_VERIFIED
import com.rogue.travelguru.core.Constants.RETURN_TO_SIGN_IN_SCREEN
import com.rogue.travelguru.core.Constants.SPAM_EMAIL

@Composable
fun VerifyEmailContent(
    padding: PaddingValues,
    reloadUser: () -> Unit,
    signOut : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                reloadUser()
            },
            text = ALREADY_VERIFIED,
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
        SmallSpacer()
        Text(
            modifier = Modifier.clickable {
                signOut()
            },
            text = RETURN_TO_SIGN_IN_SCREEN,
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
        SmallSpacer()
        Text(
            text = SPAM_EMAIL,
            fontSize = 15.sp
        )
    }
}