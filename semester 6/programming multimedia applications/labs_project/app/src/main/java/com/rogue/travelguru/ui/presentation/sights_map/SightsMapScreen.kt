package com.rogue.travelguru.ui.presentation.sights_map

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.rogue.extension.hasLocationPermission
import com.rogue.travelguru.R
import com.rogue.travelguru.components.RationaleAlert
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBar
import com.rogue.travelguru.model.LocationProvider
import com.rogue.travelguru.ui.presentation.home.SightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SightsMapScreen(
    locationViewModel : LocationViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    sightViewModel: SightViewModel,
    videoViewModel: VideoSightViewModel,
    audioViewModel: AudioSightViewModel
){
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val viewState by locationViewModel.viewState.collectAsStateWithLifecycle()


    LaunchedEffect(!context.hasLocationPermission()) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                locationViewModel.handle(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            RationaleAlert(onDismiss = { }) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                locationViewModel.handle(PermissionEvent.Revoked)
            }
        }
    }

    with(viewState) {
        when (this) {
            ViewState.Loading -> {
                Scaffold(
                    bottomBar = bottomBar,
                    content = {padding ->
                        Column(
                            modifier = Modifier.padding(padding)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AuthLoginProgressIndicator()
                            }
                        }

                    }

                )

            }

            ViewState.RevokedPermissions -> {

                Scaffold(
                    topBar = {
                        TGTopAppBar(
                            topAppBarText = stringResource(id = R.string.sights_map),
                        )
                    },
                    content = {
                            pad -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text("We need permissions to use this app")
                        Button(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            },
                            enabled = !context.hasLocationPermission()
                        ) {
                            if (context.hasLocationPermission()) CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                color = Color.White
                            )
                            else Text("Settings")
                        }
                    }
                    }
                )


            }

            is ViewState.Success -> {
                val currentLoc =
                    LatLng(
                        this.location?.latitude ?: 0.0,
                        this.location?.longitude ?: 0.0
                    )
                LocationProvider.currentLocation = currentLoc

                Scaffold(
                    topBar = {
                        TGTopAppBar(
                            topAppBarText = stringResource(id = R.string.sights_map),
                        )
                    },
                    bottomBar = bottomBar,
                    content = {
                            pad -> SightsMapContent(pad = pad,audioViewModel = audioViewModel,videoViewModel = videoViewModel, sightViewModel = sightViewModel)
                    }

                )
                Log.i("Current Location","${this.location?.latitude} ${this.location?.longitude}")
            }
        }
    }



}