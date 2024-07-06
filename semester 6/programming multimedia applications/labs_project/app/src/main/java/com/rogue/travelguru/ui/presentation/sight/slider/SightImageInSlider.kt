package com.rogue.travelguru.ui.presentation.sight.slider

import WIDTH_OF_SIGHT_BOX
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rogue.travelguru.R

@Composable
fun SightImageInSlider(item: String) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(item)
            .crossfade(true).build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = item,
        contentScale = ContentScale.Fit,
        modifier = androidx.compose.ui.Modifier
            .width(WIDTH_OF_SIGHT_BOX.dp)
    )
}