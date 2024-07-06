package com.rogue.travelguru.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SightEntity::class],
    version = 1
)
abstract class SightDatabase: RoomDatabase() {

    abstract val dao: SightDao
}