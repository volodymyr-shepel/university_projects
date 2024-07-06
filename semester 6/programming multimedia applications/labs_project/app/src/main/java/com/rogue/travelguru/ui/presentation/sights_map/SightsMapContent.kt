package com.rogue.travelguru.ui.presentation.sights_map

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.rogue.travelguru.R
import com.rogue.travelguru.components.MapMarker
import com.rogue.travelguru.components.SimpleBottomSheetScaffold
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBack
import com.rogue.travelguru.model.LocationProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.ui.presentation.home.CurrentSight
import com.rogue.travelguru.ui.presentation.home.SightLocationState
import com.rogue.travelguru.ui.presentation.home.SightViewModel
import com.rogue.travelguru.ui.presentation.sight.SightContent
import com.rogue.travelguru.ui.presentation.sight.audio.AudioRow
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import com.rogue.travelguru.ui.presentation.sight.slider.SightSlider
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator


@Composable
fun SightsMapContent(
    pad : PaddingValues,
    sightViewModel: SightViewModel,
    audioViewModel: AudioSightViewModel,
    videoViewModel: VideoSightViewModel

){
    val context = LocalContext.current

    val sights = sightViewModel.geoSightUiState
    val sight : CurrentSight = sightViewModel.specificMapSightUiState


    LaunchedEffect (LocationProvider.currentSightIdBottomSheet){
        sightViewModel.setSightMap(LocationProvider.currentSightIdBottomSheet)
    }
    
    SimpleBottomSheetScaffold(
        padding = pad,
        title =
            when (sight) {
                CurrentSight.EmptySight,CurrentSight.ErrorSight -> ""
                is CurrentSight.HasCurrentSight -> sight.sight.name
            }
        ,
        sheetContent = {
            when(sight) {
                CurrentSight.EmptySight -> AuthLoginProgressIndicator()
                CurrentSight.ErrorSight -> Text(text = "Error while loading the sight data, please try again")
                is CurrentSight.HasCurrentSight ->  SightContent(pad = PaddingValues(0.dp), currentSight = sight, audioViewModel = audioViewModel, videoViewModel = videoViewModel)
            }
        },
        content = {
            val location = LocationProvider.currentLocation
            if (LocationProvider.currentCameraPosition == null){
                LocationProvider.currentCameraPosition = LocationProvider.currentLocation
            }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LocationProvider.currentCameraPosition!!, 15f)
            }

            val uiSettings by remember {
                mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
            }
            val properties by remember {
                mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings,

            ) {
                MapMarker(
                    context = context,
                    position = location,
                    iconResourceId = R.drawable.pin,

                )

                when(sights) {
                    is SightLocationState.Success ->
                        for(loc in sights.sights) {
                            MapMarker(
                                context = context,
                                position = LatLng(loc.latitude.toDouble(), loc.longitude.toDouble()),
                                onClickMarker = {
                                    LocationProvider.currentSightIdBottomSheet = loc.id
                                    false
                                }
                            )
                        }

                    SightLocationState.EmptySight, SightLocationState.ErrorSight -> Unit
                }
            }
        }
    )

}



