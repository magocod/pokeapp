package com.example.pokeapp.network

import com.squareup.moshi.Json

data class PokemonRename(
    @Json(name = "nick_name") val nickName: String
)
