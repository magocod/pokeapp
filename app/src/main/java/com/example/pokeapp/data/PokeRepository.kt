package com.example.pokeapp.data

import com.example.pokeapp.data.model.PokemonType
import com.example.pokeapp.network.*

interface PokeRepository {
    suspend fun getRegions(): Result<List<Region>>

    suspend fun getRegionDetail(regionId: Int): Result<RegionDetail>

    suspend fun getLocationDetail(locationId: Int): Result<LocationDetail>

    suspend fun getAreaDetail(areaId: Int): Result<AreaDetail>

    suspend fun getSpecie(specieId: Int): Result<Specie>

    suspend fun pokemonCatch(token: String, pokemonCatch: PokemonCatch): Result<CapturedPokemon>

    suspend fun getPokemonParty(token: String): Result<List<UserPokemon>>

    suspend fun getPokemonStorage(token: String): Result<List<UserPokemon>>

    suspend fun pokemonRename(
        id: Int,
        token: String,
        pokemonRename: PokemonRename
    ): Result<CapturedPokemon>

    suspend fun pokemonRelease(id: Int, token: String): Result<String>

    suspend fun swapPartyMember(token: String, swapPartyMember: SwapPartyMember): Result<List<UserPokemon>>
    fun getTypeIcon(typeName: String): Int
    fun getPokemonTypes(): List<PokemonType>
}