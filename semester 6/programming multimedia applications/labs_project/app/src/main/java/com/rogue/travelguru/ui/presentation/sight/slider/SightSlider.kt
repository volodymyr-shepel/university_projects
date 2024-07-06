package com.rogue.travelguru.ui.presentation.sight.slider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.rogue.travelguru.ui.presentation.home.CurrentSight
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel

@Composable
fun SightSlider(lifecycle: Lifecycle.Event, videoViewModel: VideoSightViewModel, currentSight: CurrentSight.HasCurrentSight) {
    LazyRow {
        item {
            SliderCard {
                SightVideoInSlider(lifecycle, videoViewModel, currentSight)
            }
        }
        items(currentSight.sight.imageUrls) { item->
            SliderCard {
                SightImageInSlider(item)
            }
        }
    }
}