package com.idlenonsense.krasnodar.ui

import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place
import com.idlenonsense.krasnodar.data.hotelPlaces

data class KrasnodarAppState(
    var isShowingListPage: Boolean = true,
    var currentPlace: Place = hotelPlaces[0],
    var currentCategory: Category = Category.HOTELS
)