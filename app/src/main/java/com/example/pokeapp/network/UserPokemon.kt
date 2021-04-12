package com.example.pokeapp.network

import com.squareup.moshi.Json

/**
 * User's Pokemon
 */
data class UserPokemon(
    val id: Int,
    @Json(name = "nick_name") val nickName: String,
    @Json(name = "is_party_member") val isPartyMember: Boolean,
    @field:Json(name = "specie") val specie: Pokemon
) {
    override fun toString(): String {
        return "$nickName - ${specie.name}"
    }
}
