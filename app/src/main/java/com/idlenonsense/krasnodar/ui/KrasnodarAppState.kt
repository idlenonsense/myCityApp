package com.idlenonsense.krasnodar.ui

import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place

data class KrasnodarAppState(
    var isShowingListPage: Boolean = true,
    var currentPlace: Place = Place(
        name = "Загрузка...",
        desc = "",
        imageUrl = "",
        category = Category.HOTELS
    ),
    var currentCategory: Category = Category.HOTELS
)