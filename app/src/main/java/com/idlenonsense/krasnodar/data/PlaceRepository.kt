package com.idlenonsense.krasnodar.data

import kotlinx.coroutines.flow.Flow

class PlaceRepository(private val placeDao: PlaceDao) {
    fun getPlacesByCategory(category: Category): Flow<List<Place>> =
        placeDao.getPlacesByCategory(category)

    suspend fun insertPlaces(places: List<Place>) =
        placeDao.insertPlaces(places)
}