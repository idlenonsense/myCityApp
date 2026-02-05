package com.idlenonsense.krasnodar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places WHERE category = :category")
    fun getPlacesByCategory(category: Category): Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<Place>)

    @Query("SELECT * FROM places WHERE id = :id")
    suspend fun getPlaceById(id: Int): Place?
}