package com.rogue.travelguru.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SightEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val favourite: Boolean = false
)