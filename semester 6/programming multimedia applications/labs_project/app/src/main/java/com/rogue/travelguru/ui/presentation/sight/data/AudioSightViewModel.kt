package com.rogue.travelguru.ui.presentation.sight.data

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.rogue.travelguru.AuthLoginApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AudioSightViewModel(
    private val savedStateHandle: SavedStateHandle,
    val videoPlayer: Player,
    val audioPlayer: Player,
    private val metadataReader: MetadataReader
): ViewModel() {

    private val audioUri = savedStateHandle.getStateFlow("audioUri", Uri.EMPTY)
    private fun getAudioItem(uri: Uri): AudioItem {
        return AudioItem(
            uri,
            MediaItem.fromUri(uri),
            metadataReader.getContent(uri)?.filename ?: "No name"
        )
    }

    val audioItem = audioUri.map { uri -> getAudioItem(uri) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Uri.EMPTY)
    var isAudioRunning = mutableStateOf(false)


    init {
        audioPlayer.prepare()
        audioPlayer.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    isAudioRunning.value = isPlaying
                    if (isPlaying) {
                        videoPlayer.pause()
                    }
                }
            }
        )
    }

    fun setAudioUri(uri: Uri) {
        savedStateHandle["audioUri"] = uri
        if(audioPlayer.mediaItemCount > 0)
            audioPlayer.removeMediaItem(0)
        audioPlayer.addMediaItem(MediaItem.fromUri(uri))
    }

    fun releaseAudio() {
        if(audioPlayer.mediaItemCount > 0) {
            audioPlayer.stop()
            audioPlayer.removeMediaItem(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AuthLoginApp)

                val audioPlayer = application.container.audioPlayer
                val videoPlayer = application.container.videoPlayer

                val state = SavedStateHandle(mapOf("audioUri" to Uri.EMPTY))
                AudioSightViewModel(
                    savedStateHandle = state,
                    audioPlayer = audioPlayer,
                    videoPlayer = videoPlayer,
                    metadataReader = MetadataFileReader(application)
                )
            }
        }
    }
}