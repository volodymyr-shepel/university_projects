package com.rogue.travelguru.ui.presentation.home

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBar
import com.rogue.travelguru.data.local.Sight
import kotlinx.coroutines.runBlocking


@Composable
fun removeFromFavorites(sightViewModel: SightViewModel, id: Int) {
    Button(onClick = {
        removeFromFavs(sightViewModel, id)
    }) {
        Text("Remove")
    }
}

fun removeFromFavs(sightViewModel: SightViewModel, id: Int) {
    runBlocking {
        sightViewModel.removeFromFavourites(id)
    }
}

@Composable
fun FavouritesScreen(
    bottomBar: @Composable () -> Unit,
    navigateToSightScreen: (sight: Sight) -> () -> Unit,
    sightViewModel: SightViewModel,
    component: (sight: Sight) -> @Composable () -> Unit,
    func:  (sight: Sight) -> () -> Unit,
) {
    Scaffold (
        topBar = {
            TGTopAppBar(
            topAppBarText = "Favourites",
        )
        },
        bottomBar = bottomBar
    ) {
            pad -> HomeScreenContent(
                navigateToSightScreen = navigateToSightScreen,
                sightUiState = sightViewModel.sightFavouritesUiState,
                retryAction = sightViewModel::getFavouritesSights,
                contentPadding = pad,
                component = component,
                func = func,
                isSwipeAble = true
            )
    }
}