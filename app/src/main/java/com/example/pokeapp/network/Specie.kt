package com.example.pokeapp.network

import com.squareup.moshi.Json

data class Specie(
    val id: Int,
    val name: String,
    @field:Json(name = "stats") val stats: List<Status>,
    @field:Json(name = "sprites") val sprites: Sprites,
    @field:Json(name = "moves") val moves: List<String>,
    @field:Json(name = "types") val types: List<String>,
)
