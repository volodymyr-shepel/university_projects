package com.rogue.travelguru.ui.presentation.sight.audio

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rogue.travelguru.R
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.slider.TrackSlider
import kotlinx.coroutines.delay

@Composable
fun AudioRow(
    audioViewModel: AudioSightViewModel
) {
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

    LaunchedEffect(audioViewModel.isAudioRunning.value) {
        isPlaying.value = audioViewModel.isAudioRunning.value
    }

    LaunchedEffect(key1 = audioViewModel.audioPlayer.currentPosition, key2 = audioViewModel.audioPlayer.isPlaying) {
        delay(2000)
        currentPosition.longValue = audioViewModel.audioPlayer.currentPosition
    }

    LaunchedEffect(currentPosition.longValue) {
        sliderPosition.longValue = currentPosition.longValue
    }

    LaunchedEffect(audioViewModel.audioPlayer.duration) {
        if (audioViewModel.audioPlayer.duration > 0) {
            totalDuration.longValue = audioViewModel.audioPlayer.duration
        }
    }


    Row(modifier = Modifier.fillMaxWidth(fraction = 0.92f), verticalAlignment = Alignment.CenterVertically) {
        ControlButton(
            icon = if (isPlaying.value) R.drawable.ic_pause else R.drawable.ic_play,
            size = 35.dp,
            onClick = {
                if (isPlaying.value) {
                    audioViewModel.audioPlayer.pause()
                } else {
                    audioViewModel.audioPlayer.play()
                }
                isPlaying.value = !isPlaying.value
            }
        )
        Spacer(modifier = Modifier.width(20.dp))
        TrackSlider(
            value = sliderPosition.longValue.toFloat(),
            onValueChange = {
                sliderPosition.longValue = it.toLong()
            },
            onValueChangeFinished = {
                currentPosition.longValue = sliderPosition.longValue
                audioViewModel.audioPlayer.seekTo(sliderPosition.longValue)
            },
            songDuration = totalDuration.longValue.toFloat()
        )
    }
}