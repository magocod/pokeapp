package com.example.pokeapp.network

import com.squareup.moshi.Json

data class Area(
    val id: Int,
    val name: String,
    val location: Int,
    @Json(name = "pokemon_count") val pokemonCount: Int,
)
