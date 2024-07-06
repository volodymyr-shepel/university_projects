package com.rogue.travelguru.mediator

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rogue.travelguru.data.local.SightDao
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

suspend fun <Key : Any, Value : Any> PagingSource<Key, Value>.loadSinglePage(): List<Value> {
    val loadParams = PagingSource.LoadParams.Refresh<Key>(
        key = null,
        loadSize = 20,
        placeholdersEnabled = false
    )
    val loadResult = load(loadParams)
    return when (loadResult) {
        is PagingSource.LoadResult.Page -> {
            loadResult.data
        }
        else -> {
            throw IllegalStateException("Invalid load result: $loadResult")
        }
    }
}

@RunWith(AndroidJUnit4::class)
class SightDatabaseTest {

    private lateinit var sightDao: SightDao
    private lateinit var db: SightDatabase

    @Before
    fun createDb() = runBlocking {
        withContext(Dispatchers.IO) {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(context, SightDatabase::class.java).build()
            sightDao = db.dao
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = runBlocking {
        withContext(Dispatchers.IO) {
            db.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeSightAndReadInList() = runBlocking {
        val sight = SightEntity(
            id = 1,
            name = "Eiffel Tower",
            description = "Iconic landmark in Paris",
            imageUrl = "https://example.com/eiffel_tower.jpg",
            favourite = true
        )
        withContext(Dispatchers.IO) {
            sightDao.upsertAll(listOf(sight))
        }
        val loadedData = withContext(Dispatchers.IO) {
            sightDao.pagingSource().loadSinglePage()
        }
        assertThat(loadedData.contains(sight), equalTo(true))
    }

    @Test
    @Throws(Exception::class)
    fun addToFavourites() = runBlocking {
        val sight = SightEntity(
            id = 1,
            name = "Eiffel Tower",
            description = "Iconic landmark in Paris",
            imageUrl = "https://example.com/eiffel_tower.jpg",
            favourite = false
        )
        withContext(Dispatchers.IO) {
            sightDao.upsertAll(listOf(sight))
            sightDao.addToFavourites(1)
        }
        val updatedSight = withContext(Dispatchers.IO) {
            sightDao.getSightEntity(1)
        }
        assertThat(updatedSight.favourite, equalTo(true))
    }

    @Test
    @Throws(Exception::class)
    fun removeFromFavourites() = runBlocking {
        val sight = SightEntity(
            id = 1,
            name = "Eiffel Tower",
            description = "Iconic landmark in Paris",
            imageUrl = "https://example.com/eiffel_tower.jpg",
            favourite = true
        )
        withContext(Dispatchers.IO) {
            sightDao.upsertAll(listOf(sight))
            sightDao.removeFromFavourites(1)
        }
        val updatedSight = withContext(Dispatchers.IO) {
            sightDao.getSightEntity(1)
        }
        assertThat(updatedSight.favourite, equalTo(false))
    }

    @Test
    @Throws(Exception::class)
    fun clearAll() = runBlocking {
        val sight = SightEntity(
            id = 1,
            name = "Eiffel Tower",
            description = "Iconic landmark in Paris",
            imageUrl = "https://example.com/eiffel_tower.jpg",
            favourite = true
        )
        withContext(Dispatchers.IO) {
            sightDao.upsertAll(listOf(sight))
            sightDao.clearAll()
        }
        val loadedData = withContext(Dispatchers.IO) {
            sightDao.pagingSource().loadSinglePage()
        }
        assertThat(loadedData.isEmpty(), equalTo(true))
    }
}
