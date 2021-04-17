package com.example.pokeapp.data

import com.example.pokeapp.data.model.PokemonType
import com.example.pokeapp.network.*

class FakePokemonRemoteDataSource(
    var regions: MutableList<Region>? = mutableListOf(),
    var locations: MutableList<Location>? = mutableListOf(),
    var areas: MutableList<Area>? = mutableListOf(),
    var userPokemons: MutableList<UserPokemon>? = mutableListOf(),
    var types: MutableList<PokemonType>? = mutableListOf()
    ): PokeRepository {
    override suspend fun getRegions(): Result<List<Region>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRegionDetail(regionId: Int): Result<RegionDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationDetail(locationId: Int): Result<LocationDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getAreaDetail(areaId: Int): Result<AreaDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecie(specieId: Int): Result<Specie> {
        TODO("Not yet implemented")
    }

    override suspend fun pokemonCatch(
        token: String,
        pokemonCatch: PokemonCatch,
    ): Result<CapturedPokemon> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonParty(token: String): Result<List<UserPokemon>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonStorage(token: String): Result<List<UserPokemon>> {
        TODO("Not yet implemented")
    }

    override suspend fun pokemonRename(
        id: Int,
        token: String,
        pokemonRename: PokemonRename,
    ): Result<CapturedPokemon> {
        TODO("Not yet implemented")
    }

    override suspend fun pokemonRelease(id: Int, token: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun swapPartyMember(
        token: String,
        swapPartyMember: SwapPartyMember,
    ): Result<List<UserPokemon>> {
        TODO("Not yet implemented")
    }

    override fun getTypeIcon(typeName: String): Int {
        TODO("Not yet implemented")
    }

    override fun getPokemonTypes(): List<PokemonType> {
        TODO("Not yet implemented")
    }
}