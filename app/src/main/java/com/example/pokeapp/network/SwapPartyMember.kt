package com.example.pokeapp.network

import com.squareup.moshi.Json

data class SwapPartyMember(
    @Json(name = "entering_the_party") val enteringTheParty: Int,
    @Json(name = "leaving_the_party") val leavingTheParty: Int,
)
