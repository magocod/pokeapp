package com.example.pokeapp.network

import com.squareup.moshi.Json

data class Pokemon(
    val id: Int,
    val name: String,
    @field:Json(name = "sprites") val sprites: Sprites,
)
