package com.rogue.travelguru.data

import com.rogue.travelguru.model.SightDataLocation
import com.rogue.travelguru.model.SightDataResource
import com.rogue.travelguru.model.SightPhoto
import com.rogue.travelguru.network.sight.SightApiService


/**
 * Repository that fetch mars photos list from marsApi.
 */
interface SightPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getSightPhotos(page: Int, pageCount: Int): List<SightPhoto>
    suspend fun getSightData(id: Int): SightDataResource

    suspend fun getSightLocationData(): List<SightDataLocation>
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */
class NetworkSightPhotosRepository(
    private val sightApiService: SightApiService
) : SightPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getSightPhotos(page: Int, pageCount: Int): List<SightPhoto> = sightApiService.getSights(page, pageCount)
    override suspend fun getSightData(id: Int): SightDataResource = sightApiService.getSight(id)

    override suspend fun getSightLocationData(): List<SightDataLocation> = sightApiService.getSightLocation()

}