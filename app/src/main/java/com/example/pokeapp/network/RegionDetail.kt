package com.example.pokeapp.network

import com.squareup.moshi.Json

data class RegionDetail(
    val id: Int,
    val name: String,
    @field:Json(name = "locations") val locations: List<Location>
)
