package com.rogue.travelguru.ui.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    sightUiState: SightUiState,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigateToSightScreen: (sight: Sight) -> () -> Unit,
    component: (sight: Sight) -> @Composable () -> Unit = {{}},
    func:  (sight: Sight) -> () -> Unit = {{}},
    isSwipeAble: Boolean = false
    ) {

    Column(
        modifier = Modifier.padding(contentPadding)
    ) {

        when (sightUiState) {
            is SightUiState.Loading -> AuthLoginProgressIndicator()
            is SightUiState.Success -> SightPhotosScreen(
                sightUiState.sights.collectAsLazyPagingItems(), contentPadding = contentPadding, modifier = modifier.fillMaxWidth(),
                navigateToSight = navigateToSightScreen, component = component, func = func, isSwipeAble = isSwipeAble
            )
            is SightUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}



