package com.rogue.travelguru.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

import kotlinx.serialization.Serializable

data class SightToSee(
    @StringRes val stringResourceId: Int,
    @StringRes val stringDescriptionId: Int,
    @DrawableRes val imageResourceId: Int
)

@Serializable
data class SightPhoto(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

@Serializable
data class SightDataResource(
    val id: Int,
    val name: String,
    val description: String,
    val longDescription: String,
    val videoUrl: String,
    val audioUrl: String,
    val imageUrl: List<String>,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class SightDataLocation(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)