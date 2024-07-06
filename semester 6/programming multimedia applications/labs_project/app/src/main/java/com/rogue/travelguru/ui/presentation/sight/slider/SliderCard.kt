package com.rogue.travelguru.ui.presentation.sight.slider

import BORDER_RADIUS_OF_SIGHT_BOX
import PADDING_OF_SIGHT_BOX
import WIDTH_OF_SIGHT_BOX
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SliderCard(part: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .width(
                (WIDTH_OF_SIGHT_BOX).dp
            )
            .aspectRatio(16 / 9f)
            .background(
                color = Color(0, 0, 0, 0),
                shape = RoundedCornerShape((BORDER_RADIUS_OF_SIGHT_BOX - PADDING_OF_SIGHT_BOX).dp)
            ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        part()
    }
}