package com.rogue.travelguru.data.mappers

import com.rogue.travelguru.data.local.SightEntity

import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.data.local.SightData
import com.rogue.travelguru.data.local.SightLocation
import com.rogue.travelguru.model.SightDataLocation
import com.rogue.travelguru.model.SightDataResource
import com.rogue.travelguru.model.SightPhoto

fun SightDataLocation.toSightLocation(): SightLocation {
    return SightLocation(id, name, latitude, longitude)
}

fun SightDataResource.toSightData(): SightData {
    return SightData(id, name, description, longDescription, videoUrl, audioUrl, imageUrl,latitude, longitude)
}

fun SightPhoto.toSightEntity(): SightEntity {
    return SightEntity(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )
}

fun SightEntity.toSight(): Sight {
    return Sight(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )
}