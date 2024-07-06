package com.rogue.travelguru.ui.presentation.home

import BORDER_RADIUS_OF_SIGHT_BOX
import HEIGHT_OF_SIGHT_BOX
import PADDING_OF_SIGHT_BOX
import WIDTH_OF_SIGHT_BOX
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import build
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rogue.travelguru.R
import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.ui.theme.WhiteGradientEnd
import com.rogue.travelguru.ui.theme.WhiteGradientStart


@Composable
fun SightPhotoCard(
    sight: Sight,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    component: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Card(
            modifier = modifier.build(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x10EBEBF0)
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .background(color = Color(0x00000000)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(sight.imageUrl)
                        .crossfade(true).build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = sight.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(125.dp)
                        .width(116.dp)
                        .clip(RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - (10 - PADDING_OF_SIGHT_BOX)).dp))
                )
                Text(
                    text = sight.description,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        component()
    }
}

@Preview
@Composable
private fun SightCardPreview() {
    SightPhotoCard(
        Sight(1, "HI", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "https://en.wikipedia.org/wiki/Main_Page#/media/File:Flag_of_the_New_California_Republic.svg"),
        onClick = {},
        component = {}
    )
}