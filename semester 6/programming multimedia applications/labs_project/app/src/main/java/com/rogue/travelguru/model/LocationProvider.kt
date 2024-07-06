package com.rogue.travelguru.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

object LocationProvider {
    var currentLocation by mutableStateOf<LatLng>(LatLng(0.0,0.0))
    var currentSightIdBottomSheet by mutableIntStateOf(1)
    var currentCameraPosition by mutableStateOf<LatLng?>(null)
}