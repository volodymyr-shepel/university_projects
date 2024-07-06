package com.rogue.travelguru.data

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rogue.travelguru.network.sight.SightApiService
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val sightPhotosRepository: SightPhotosRepository
    fun sightDatabase(): SightDatabase
    fun sightPager(sightDatabase: SightDatabase): Pager<Int, SightEntity>
    val videoPlayer: ExoPlayer
    val audioPlayer: ExoPlayer
    fun sightPagerForFavourites(sightDatabase: SightDatabase): Pager<Int, SightEntity>
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */

@OptIn(ExperimentalPagingApi::class)

class DefaultAppContainer(val context: Context): AppContainer {
    private val baseUrl = "http://192.168.137.2:8080"

    override val videoPlayer: ExoPlayer = ExoPlayer.Builder(context)
        .build()

    override val audioPlayer: ExoPlayer = ExoPlayer.Builder(context)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: SightApiService by lazy {
        retrofit.create(SightApiService::class.java)
    }

    override val sightPhotosRepository: SightPhotosRepository by lazy {
        NetworkSightPhotosRepository(retrofitService)
    }

    override fun sightDatabase(): SightDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SightDatabase::class.java,
            "sight3.db"
        ).build()
    }

    override fun sightPager(sightDatabase: SightDatabase): Pager<Int, SightEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = SightRemoteMediator(
                sightDb = sightDatabase,
                sightApi = sightPhotosRepository
            ),
            pagingSourceFactory = {
                sightDatabase.dao.pagingSource()
            }
        )
    }

    override fun sightPagerForFavourites(sightDatabase: SightDatabase): Pager<Int, SightEntity> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            remoteMediator = FavouritesRemoteMediator(
                sightDb = sightDatabase,
                sightApi = sightPhotosRepository
            ),
            pagingSourceFactory = {
                sightDatabase.dao.pagingSourceForFavourites()
            }
        )
    }


}