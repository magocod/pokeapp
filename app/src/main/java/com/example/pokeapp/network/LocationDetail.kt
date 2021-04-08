package com.example.pokeapp.network

import com.squareup.moshi.Json

data class LocationDetail(
    val id: Int,
    val name: String,
    val region: Int,
    @field:Json(name = "areas") val areas: List<Area>
)
