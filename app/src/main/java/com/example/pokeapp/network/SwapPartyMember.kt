package com.example.pokeapp.network

import com.squareup.moshi.Json

data class SwapPartyMember(
    @Json(name = "entering_the_party") val enteringTheParty: Int? = null,
    @Json(name = "leaving_the_party") val leavingTheParty: Int? = null,
)

//data class SwapPartyMember(
//    @SerializedName("entering_the_party") val enteringTheParty: Int?,
//    @SerializedName("leaving_the_party") val leavingTheParty: Int?,
//)
