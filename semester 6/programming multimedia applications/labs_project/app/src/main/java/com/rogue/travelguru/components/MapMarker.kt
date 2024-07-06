package com.rogue.travelguru.components

import android.content.Context
import android.content.Intent.ShortcutIconResource
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.rogue.travelguru.R

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    @DrawableRes iconResourceId: Int? = null ,
    onClickMarker: (Marker) -> Boolean = { false }
){

    val icon = iconResourceId?.let { bitmapDescriptorFromVector(context, it) }

    Marker(
        state = MarkerState(position = position),
        icon = icon,
        onClick = onClickMarker
    )


}

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}