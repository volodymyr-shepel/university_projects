package com.rogue.travelguru.ui.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.withTransaction
import com.rogue.travelguru.AuthLoginApp
import com.rogue.travelguru.data.SightPhotosRepository
import com.rogue.travelguru.data.local.Sight
import com.rogue.travelguru.data.local.SightData
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import com.rogue.travelguru.data.mappers.toSight
import com.rogue.travelguru.data.mappers.toSightData
import com.rogue.travelguru.model.SightDataLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException


sealed interface SightLocationState {
    data class Success(val sights: List<SightDataLocation>) : SightLocationState
    object EmptySight : SightLocationState

    object ErrorSight : SightLocationState
}

sealed interface SightUiState {
    data class Success(val sights: Flow<PagingData<Sight>>) : SightUiState
    object Error : SightUiState
    object Loading : SightUiState
}

sealed interface CurrentSight {
    data class HasCurrentSight(val sight: SightData) : CurrentSight
    object EmptySight : CurrentSight

    object ErrorSight : CurrentSight
}

open class SightViewModel(private val pagerSight: Pager<Int, SightEntity>, private val pagerFavourites: Pager<Int, SightEntity>,open val sightPhotosRepository: SightPhotosRepository, open val sightDatabase: SightDatabase) : ViewModel() {
    var sightUiState: SightUiState by mutableStateOf(SightUiState.Loading)
        protected set

    var sightFavouritesUiState: SightUiState by mutableStateOf(SightUiState.Loading)
        protected set

    var specificSightUiState: CurrentSight by mutableStateOf(CurrentSight.EmptySight)
        private set

    var specificMapSightUiState: CurrentSight by mutableStateOf(CurrentSight.EmptySight)
        private set

    var geoSightUiState: SightLocationState by mutableStateOf(SightLocationState.EmptySight)
        private set

    init {
        getSights()
        getFavouritesSights()
        setSightLocation()
    }

    fun getSights() {
        viewModelScope.launch {
            sightUiState = SightUiState.Loading
            sightUiState = try {
                SightUiState.Success(
                    pagerSight
                        .flow
                        .map {
                            pagingData -> pagingData.map {
                                it.toSight()
                            }
                        }
                        .cachedIn(viewModelScope))
            } catch (e: IOException) {
                SightUiState.Error
            } catch (e: HttpException) {
                SightUiState.Error
            }
        }
    }

    fun getFavouritesSights() {
        viewModelScope.launch {
            sightFavouritesUiState = SightUiState.Loading
            sightFavouritesUiState = try {
                SightUiState.Success(
//                    {
//                        val x = sightDatabase.dao.pagingSourceForFavourites().map { pagingData ->
//                            pagingData.toSight()
//                        }
//                        return x
//                    }
//                )
                    pagerFavourites.flow
                        .map {
                            pagingData -> pagingData.map {
                                it.toSight()
                            }
                        }
                        .cachedIn(viewModelScope))
            } catch (e: IOException) {
                SightUiState.Error
            } catch (e: HttpException) {
                SightUiState.Error
            }
        }
    }

    fun setSightLocation() {
        viewModelScope.launch {
            //TODO: prepare logic to read specific Sight
            geoSightUiState = SightLocationState.EmptySight
            geoSightUiState = try {
                SightLocationState.Success(
                    sightPhotosRepository.getSightLocationData()
                )
            }
            catch (e: IOException) {
                SightLocationState.ErrorSight
            } catch (e: HttpException) {
                SightLocationState.ErrorSight
            }

        }
    }

    suspend fun addToFavourites(id: Int) {
        sightDatabase.withTransaction {
            sightDatabase.dao.addToFavourites(id)
        }
    }


    suspend fun removeFromFavourites(id: Int) {
        sightDatabase.withTransaction {
            sightDatabase.dao.removeFromFavourites(id)
        }
    }

    fun setSightMap(id: Int) {
        viewModelScope.launch {
            //TODO: prepare logic to read specific Sight
            specificMapSightUiState = CurrentSight.EmptySight
            specificMapSightUiState = try {
                CurrentSight.HasCurrentSight(
                    sightPhotosRepository.getSightData(id).toSightData()
                )
            }
            catch (e: IOException) {
                CurrentSight.ErrorSight
            } catch (e: HttpException) {
                CurrentSight.ErrorSight
            }

        }
    }

    fun setSight(id: Int) {
        viewModelScope.launch {
            //TODO: prepare logic to read specific Sight
            specificSightUiState = CurrentSight.EmptySight
            specificSightUiState = try {
                CurrentSight.HasCurrentSight(
                    sightPhotosRepository.getSightData(id).toSightData()
                )
            }
            catch (e: IOException) {
                CurrentSight.ErrorSight
            } catch (e: HttpException) {
                CurrentSight.ErrorSight
            }

        }
    }

    fun setSight(sight: Sight) {
        setSight(sight.id)
    }

    fun setSight(sight: SightDataLocation) {
        setSight(sight.id)
    }

    fun clearCurrentSight() {
        viewModelScope.launch {
            specificSightUiState = CurrentSight.EmptySight
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthLoginApp)
                val sightDatabase = application.container.sightDatabase()
                val sightPager = application.container.sightPager(sightDatabase)
                val favPager = application.container.sightPagerForFavourites(sightDatabase)
                val sightPhotosRepository = application.container.sightPhotosRepository
                SightViewModel(pagerSight = sightPager, sightPhotosRepository = sightPhotosRepository, pagerFavourites = favPager,sightDatabase = sightDatabase)
            }
        }
    }
}