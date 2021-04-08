package com.example.pokeapp.network

import com.squareup.moshi.Json

data class CapturedPokemon(
    val id: Int,
    @Json(name = "nick_name") val nickName: String,
    @Json(name = "is_party_member") val isPartyMember: Boolean,
    val specie: Int,
)
