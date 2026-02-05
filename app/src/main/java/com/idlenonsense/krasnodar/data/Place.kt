package com.idlenonsense.krasnodar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val desc: String,
    val imageUrl: String,
    val category: Category
)


