package com.idlenonsense.krasnodar.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.idlenonsense.krasnodar.R

data class Place(
    @StringRes
    val name: Int,
    @StringRes
    val desc: Int,
    @DrawableRes
    val image: Int
)

val foodPlaces = listOf(
    Place(R.string.foodplacename1, R.string.foodplacedesc1, R.drawable.unipizza),
    Place(R.string.foodplacename2, R.string.foodplacedesc2, R.drawable.krdparen),
    Place(R.string.foodplacename3, R.string.foodplacedesc3, R.drawable.tori)
)

val funPlaces = listOf(
    Place(R.string.funplacename1, R.string.funplacedesc1, R.drawable.karo),
    Place(R.string.funplacename2, R.string.funplacedesc2, R.drawable.rockbar),
    Place(R.string.funplacename3, R.string.funplacedesc3, R.drawable.marta)
)

val hotelPlaces = listOf(
    Place(R.string.hotelplacename1, R.string.hotelplacedesc1, R.drawable.krdplaza),
    Place(R.string.hotelplacename2, R.string.hotelplacedesc2, R.drawable.arthotel),
    Place(R.string.hotelplacename3, R.string.hotelplacedesc3, R.drawable.hollydl)
)

val visitPlaces = listOf(
    Place(R.string.visitplacename1, R.string.visitplacedesc1, R.drawable.kissbridge),
    Place(R.string.visitplacename2, R.string.visitplacedesc2, R.drawable.parkkrd),
    Place(R.string.visitplacename3, R.string.visitplacedesc3, R.drawable.germanvillage)
)
