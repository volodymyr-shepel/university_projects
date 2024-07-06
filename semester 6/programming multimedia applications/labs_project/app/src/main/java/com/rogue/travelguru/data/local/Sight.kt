package com.rogue.travelguru.data.local


data class SightLocation(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class Sight(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

data class SightData(
    val id: Int,
    val name: String,
    val description: String,
    val longDescription: String,
    val videoUrl: String,
    val audioUrl: String,
    val imageUrls: List<String>,
    val latitude: Double,
    val longitude: Double

)