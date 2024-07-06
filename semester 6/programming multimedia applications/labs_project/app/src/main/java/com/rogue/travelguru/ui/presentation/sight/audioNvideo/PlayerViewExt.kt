package com.rogue.travelguru.ui.presentation.sight.audioNvideo

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.media3.ui.PlayerView

class PlayerViewExt(context: Context, var isPlaying: MutableState<Boolean>) : PlayerView(context) {

    override fun onPause() {
        super.onPause()
        isPlaying.value = false
    }

    override fun onResume() {
        super.onResume()
        isPlaying.value = true
    }
}