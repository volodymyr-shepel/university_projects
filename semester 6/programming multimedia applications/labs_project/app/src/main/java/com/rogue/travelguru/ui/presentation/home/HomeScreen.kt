package com.rogue.travelguru.ui.presentation.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rogue.travelguru.R
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBar
import com.rogue.travelguru.data.local.Sight

@Composable
fun HomeScreen(bottomBar: @Composable () -> Unit, navigateToSightScreen: (sight: Sight) -> () -> Unit, sightViewModel: SightViewModel) {
    Scaffold (
        topBar = {TGTopAppBar(
            topAppBarText = stringResource(id = R.string.home),
        )},
        bottomBar = bottomBar
    ) {
        pad -> HomeScreenContent(
            navigateToSightScreen = navigateToSightScreen,
            sightUiState = sightViewModel.sightUiState,
            retryAction = sightViewModel::getSights,
            contentPadding = pad
        )
    }
}