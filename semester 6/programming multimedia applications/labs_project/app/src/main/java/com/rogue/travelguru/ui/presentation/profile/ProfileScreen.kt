package com.rogue.travelguru.ui.presentation.profile

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rogue.travelguru.R
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBar
import com.rogue.travelguru.model.DataProvider

@Composable
fun ProfileScreen(
    bottomBar: @Composable () -> Unit,
    navigateToSignInScreen: () -> Unit,

){

        Scaffold(
            topBar = { TGTopAppBar(topAppBarText = stringResource(id = R.string.my_profile)) },
            content = {
                      pad ->
                ProfileScreenContent(navigateToSignInScreen = navigateToSignInScreen, pad = pad)

            },
            bottomBar = bottomBar
        )
}


