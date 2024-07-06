package com.rogue.travelguru.ui.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rogue.travelguru.R

@Composable
fun AnonymousUserProfileContent(
    pad : PaddingValues,
    navigateToSignInScreen: () -> Unit,
) {

    Column (
        modifier = Modifier
            .padding(pad)
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = stringResource(id = R.string.sign_in_to_view_data)
        )
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        Button(
            onClick = {
                navigateToSignInScreen()
            },
            modifier = Modifier
                .size(width = 160.dp, height = 50.dp)
                .padding(horizontal = 16.dp),


        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                modifier = Modifier.padding(6.dp),
            )
        }
    }

}
