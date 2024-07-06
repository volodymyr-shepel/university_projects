package com.rogue.travelguru.network.sight

import com.rogue.travelguru.model.SightDataLocation
import com.rogue.travelguru.model.SightDataResource
import com.rogue.travelguru.model.SightPhoto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getSights] method
 */
interface SightApiService {
    /**
     * Returns a [List] of [SightPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("sights")
    suspend fun getSights(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<SightPhoto>

    @GET("sight")
    suspend fun getSight(
        @Query("index") index: Int
    ): SightDataResource

    @GET("sight/location")
    suspend fun getSightLocation(
    ): List<SightDataLocation>
}