@file:OptIn(ExperimentalPagingApi::class)

package com.rogue.travelguru.mediator

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rogue.travelguru.data.SightPhotosRepository
import com.rogue.travelguru.data.SightRemoteMediator
import com.rogue.travelguru.data.local.SightDao
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import com.rogue.travelguru.model.SightDataLocation
import com.rogue.travelguru.model.SightDataResource
import com.rogue.travelguru.model.SightPhoto
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockSightPhotosRepository : SightPhotosRepository {

        private var sightPhotos: List<SightPhoto> = emptyList()
        private var sightDataResource: SightDataResource? = null
        private var sightLocationData: List<SightDataLocation> = emptyList()
        private var shouldThrowIOException: Boolean = false
        private var shouldThrowHttpException: Boolean = false

        fun setSightPhotos(sightPhotos: List<SightPhoto>) {
            this.sightPhotos = sightPhotos
        }

        fun setSightDataResource(sightDataResource: SightDataResource) {
            this.sightDataResource = sightDataResource
        }

        fun setSightLocationData(sightLocationData: List<SightDataLocation>) {
            this.sightLocationData = sightLocationData
        }

        fun setShouldThrowIOException(shouldThrow: Boolean) {
            this.shouldThrowIOException = shouldThrow
        }

        fun setShouldThrowHttpException(shouldThrow: Boolean) {
            this.shouldThrowHttpException = shouldThrow
        }

        override suspend fun getSightPhotos(page: Int, pageCount: Int): List<SightPhoto> {
            if (shouldThrowIOException) {
                throw IOException("Mocked IO Exception")
            }
            if (shouldThrowHttpException) {
                throw HttpException(Response.error<SightPhoto>(
                    404,
                    ResponseBody.create("text/plain".toMediaTypeOrNull(), "Mocks")
                ))
            }
            return sightPhotos
        }

        override suspend fun getSightData(id: Int): SightDataResource {
            sightDataResource?.let { return it }
            // Provide a default behavior if sightDataResource is not set
            return SightDataResource(1, "Resource", "Desc", "Long Desc", "videoUrl", "audioUrl", emptyList(), 40.7128, -74.0060)

        }

        override suspend fun getSightLocationData(): List<SightDataLocation> {
            return sightLocationData
        }
}

@RunWith(AndroidJUnit4::class)
class SightRemoteMediatorTest {

    private lateinit var sightDb: SightDatabase
    private lateinit var sightApi: MockSightPhotosRepository
    private lateinit var sightDao: SightDao
    private lateinit var remoteMediator: SightRemoteMediator

    @Before
    fun setup() {
        runBlocking {
            withContext(Dispatchers.IO) {
                val context = ApplicationProvider.getApplicationContext<Context>()
                sightDb = Room.inMemoryDatabaseBuilder(context, SightDatabase::class.java).build()
                sightDao = sightDb.dao
                sightApi = MockSightPhotosRepository()

                remoteMediator = SightRemoteMediator(
                    sightDb = sightDb,
                    sightApi = sightApi
                )
            }
        }
    }

    @Test
    fun loadRefreshSuccess() = runTest {

        sightApi.setSightPhotos(emptyList())

        val pagingState = PagingState<Int, SightEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun loadAppendSuccess() = runTest {
        val sights = listOf(
            SightPhoto(id = 20, name = "Last", description = "Last Desc", imageUrl = "lastUrl")
        )
        sightApi.setSightPhotos(sights)

        val lastItem = SightEntity(id = 20, name = "Last", description = "Last Desc", imageUrl = "lastUrl")
        val pagingState = PagingState<Int, SightEntity>(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(lastItem),
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        sightApi.setSightPhotos(emptyList())
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun loadPrependReturnsEndOfPagination() = runTest {
        sightApi.setSightPhotos(emptyList())
        val pagingState = PagingState<Int, SightEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.PREPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun loadRefreshThrowsIOException() = runTest {
        sightApi.setSightPhotos(emptyList())
        sightApi.setShouldThrowIOException(true)

        val pagingState = PagingState<Int, SightEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        sightApi.setShouldThrowIOException(false)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is IOException)

    }

    @Test
    fun loadRefreshThrowsHttpException() = runTest {
        sightApi.setSightPhotos(emptyList())
        sightApi.setShouldThrowHttpException(true)

        val pagingState = PagingState<Int, SightEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is HttpException)
        sightApi.setShouldThrowHttpException(false)
    }
}
