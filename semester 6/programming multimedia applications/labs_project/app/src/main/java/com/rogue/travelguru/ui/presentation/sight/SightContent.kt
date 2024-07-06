package com.rogue.travelguru.ui.presentation.sight

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.rogue.travelguru.components.topNavigationBars.TGTopAppBarWithArrowBack
import com.rogue.travelguru.model.LocationProvider
import com.rogue.travelguru.ui.presentation.home.CurrentSight
import com.rogue.travelguru.ui.presentation.sight.audio.AudioRow
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import com.rogue.travelguru.ui.presentation.sight.slider.SightSlider

@Composable
fun SightContent(pad : PaddingValues, currentSight: CurrentSight.HasCurrentSight, audioViewModel: AudioSightViewModel, videoViewModel: VideoSightViewModel) {

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver {
                _, event -> lifecycle = event
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

        Column(
            modifier = Modifier.padding(pad).fillMaxWidth(fraction = 0.95f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            audioViewModel.setAudioUri(Uri.parse(currentSight.sight.audioUrl))
            audioViewModel.audioPlayer.prepare()
            //Init duration and we good to go
            audioViewModel.audioPlayer.play()
            audioViewModel.audioPlayer.pause()

            Text(currentSight.sight.description)
            Spacer(modifier = Modifier.height(10.dp))
            SightSlider(lifecycle, videoViewModel, currentSight)
            Spacer(Modifier.padding(10.dp))
            AudioRow(audioViewModel)
            Spacer(modifier = Modifier.height(10.dp))
            Text(currentSight.sight.longDescription)
            Spacer(modifier = Modifier.height(10.dp))
            Text(currentSight.sight.longDescription)
            Spacer(modifier = Modifier.height(10.dp))
            Text(currentSight.sight.longDescription)
            Spacer(modifier = Modifier.height(10.dp))
            Text(currentSight.sight.longDescription)

        }

}
