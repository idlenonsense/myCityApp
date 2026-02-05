package com.idlenonsense.krasnodar

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.idlenonsense.krasnodar.data.CategoryConverter
import com.idlenonsense.krasnodar.data.Place
import com.idlenonsense.krasnodar.data.PlaceDao

@Database(entities = [Place::class], version = 1)
@TypeConverters(CategoryConverter::class)
abstract class KrasnodarDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}