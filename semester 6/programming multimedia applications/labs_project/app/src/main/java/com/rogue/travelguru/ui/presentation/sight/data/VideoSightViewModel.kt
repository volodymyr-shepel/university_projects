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

class VideoSightViewModel(
    private val savedStateHandle: SavedStateHandle,
    val videoPlayer: Player,
    val audioPlayer: Player,
    private val metadataReader: MetadataReader
): ViewModel() {

    private val videoUri = savedStateHandle.getStateFlow("videoUri", Uri.EMPTY)
    private fun getVideoItem(uri: Uri): VideoItem {
        return VideoItem(
            uri,
            MediaItem.fromUri(uri),
            metadataReader.getContent(uri)?.filename ?: "No name"
        )
    }

    val videoItem = videoUri.map { uri -> getVideoItem(uri) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Uri.EMPTY)

    var isVideoRunning = mutableStateOf(false)

    init {
        videoPlayer.prepare()
        videoPlayer.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    isVideoRunning.value = isPlaying
                    if (isPlaying) {
                        audioPlayer.pause()
                    }
                }
            }
        )
    }

    fun setVideoUri(uri: Uri) {
        savedStateHandle["videoUri"] = uri
        if(videoPlayer.mediaItemCount > 0)
            videoPlayer.removeMediaItem(0)
        videoPlayer.addMediaItem(MediaItem.fromUri(uri))
    }

    fun releaseVideo() {
        if(videoPlayer.mediaItemCount > 0) {
            videoPlayer.stop()
            videoPlayer.removeMediaItem(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AuthLoginApp)
                val videoPlayer = application.container.videoPlayer
                val audioPlayer = application.container.audioPlayer
                val state = SavedStateHandle(mapOf("videoUri" to Uri.EMPTY))
                VideoSightViewModel(
                    savedStateHandle = state,
                    videoPlayer = videoPlayer,
                    audioPlayer = audioPlayer,
                    metadataReader = MetadataFileReader(application)
                )
            }
        }
    }
}