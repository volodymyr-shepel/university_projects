package com.rogue.travelguru.ui.presentation.sight.slider

import android.net.Uri
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import com.rogue.travelguru.ui.presentation.home.CurrentSight
import com.rogue.travelguru.ui.presentation.sight.audioNvideo.PlayerViewExt
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import kotlinx.coroutines.delay

@Composable
fun SightVideoInSlider(lifecycle: Lifecycle.Event, videoViewModel: VideoSightViewModel, currentSight: CurrentSight.HasCurrentSight) {

    val isPlaying = remember {
        mutableStateOf(false)
    }

    val currentPosition = remember {
        mutableLongStateOf(0)
    }

    val sliderPosition = remember {
        mutableLongStateOf(0)
    }

    val totalDuration = remember {
        mutableLongStateOf(0)
    }

    LaunchedEffect(key1 = videoViewModel.videoPlayer.currentPosition, key2 = videoViewModel.videoPlayer.isPlaying) {
        delay(1000)
        currentPosition.longValue = videoViewModel.videoPlayer.currentPosition
    }

    LaunchedEffect(currentPosition.longValue) {
        sliderPosition.longValue = currentPosition.longValue
    }

    LaunchedEffect(videoViewModel.videoPlayer.duration) {
        if (videoViewModel.videoPlayer.duration > 0) {
            totalDuration.longValue = videoViewModel.videoPlayer.duration
        }
    }

    AndroidView(
        factory = { context ->
            PlayerViewExt(context, isPlaying).also {
                videoViewModel.setVideoUri(Uri.parse(currentSight.sight.videoUrl))
                it.player = videoViewModel.videoPlayer
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                    isPlaying.value = false
                }
                Lifecycle.Event.ON_STOP -> {
                    it.onPause()
                    it.player?.pause()
                    isPlaying.value = false
                }
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                    isPlaying.value = true
                }
                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    )
}