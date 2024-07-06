package com.rogue.travelguru.ui.presentation.sight

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.android.gms.maps.model.LatLng
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBack
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBackAndExtraComponent
import com.rogue.travelguru.model.LocationProvider
import com.rogue.travelguru.ui.presentation.home.CurrentSight
import com.rogue.travelguru.ui.presentation.home.SightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import kotlinx.coroutines.runBlocking
import com.rogue.travelguru.R

@Composable
fun AddToFavorites(sightViewModel: SightViewModel, id: Int) {
        Button(onClick = {
            runBlocking {
                sightViewModel.addToFavourites(id)
            }
        },
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(painter = painterResource(R.drawable.like_icon), contentDescription = "Like", modifier = Modifier.size(24.dp))
        }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SightScreen(navigateBack: () -> Unit,navigateToSightMapsScreen : () -> Unit, sightViewModel: SightViewModel, videoViewModel: VideoSightViewModel, audioViewModel: AudioSightViewModel) {
    when (val currentSight: CurrentSight = sightViewModel.specificSightUiState) {
        is CurrentSight.HasCurrentSight -> {
            Scaffold(topBar = {
                TGTopAppBarWithArrowBackAndExtraComponent(
                    topAppBarText = currentSight.sight.name,
                    onNavUp = navigateBack,
                    component = {  }
                )
            },
                content = { pad ->
                   Box(modifier =
                   Modifier
                       .fillMaxSize()
                       .zIndex(10f),
                       contentAlignment = Alignment.BottomEnd
                   )
                   {
                       AddToFavorites(sightViewModel, currentSight.sight.id)
                   }
                    Column(
                            modifier = Modifier
                                .padding(pad)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SightContent(
                                PaddingValues(0.dp),
                                currentSight,
                                audioViewModel,
                                videoViewModel
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = {
                                LocationProvider.currentSightIdBottomSheet = currentSight.sight.id
                                LocationProvider.currentCameraPosition = LatLng(
                                    currentSight.sight.latitude,
                                    currentSight.sight.longitude
                                )
                                navigateToSightMapsScreen()
                            }) {
                                Text(text = "Check out on map")
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }


                }
            )

        }
        is CurrentSight.EmptySight, CurrentSight.ErrorSight -> Scaffold(topBar = {
            TGTopAppBarWithArrowBack(
                topAppBarText = "Oops it's empty here",
                onNavUp = navigateBack,
            )
        }, content = { pad ->
            Text(text = "$pad Sight Screen")
        })
    }
}