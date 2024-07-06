package com.rogue.travelguru.mediator

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rogue.travelguru.data.FavouritesRemoteMediator
import com.rogue.travelguru.data.local.SightDao
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class FavouritesRemoteMediatorTest {

    private lateinit var sightDb: SightDatabase
    private lateinit var sightApi: MockSightPhotosRepository
    private lateinit var sightDao: SightDao
    private lateinit var remoteMediator: FavouritesRemoteMediator

    @Before
    fun setup() {
        runBlocking {
            withContext(Dispatchers.IO) {
                val context = ApplicationProvider.getApplicationContext<Context>()
                sightDb = Room.inMemoryDatabaseBuilder(context, SightDatabase::class.java).build()
                sightDao = sightDb.dao
                sightApi = MockSightPhotosRepository()

                remoteMediator = FavouritesRemoteMediator(
                    sightDb = sightDb,
                    sightApi = sightApi
                )
            }
        }
    }

    @Test
    fun `load returns MediatorResult_Success when end of pagination is reached`() = runTest {
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
    fun `load returns MediatorResult_Error when IOException is thrown`() = runTest {
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
    fun `load returns MediatorResult_Error when HttpException is thrown`() = runTest {
        sightApi.setShouldThrowHttpException(true)

        val pagingState = PagingState<Int, SightEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        sightApi.setShouldThrowHttpException(false)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is HttpException)
    }
}
