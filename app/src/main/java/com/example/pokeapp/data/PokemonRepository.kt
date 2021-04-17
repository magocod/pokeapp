package com.example.pokeapp.data

import com.example.pokeapp.data.model.PokemonType
import com.example.pokeapp.network.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokeRepository {

    override suspend fun getRegions(): Result<List<Region>> {
        return pokemonRemoteDataSource.getRegions()
    }

    override suspend fun getRegionDetail(regionId: Int): Result<RegionDetail> {
        return pokemonRemoteDataSource.getRegionDetail(regionId)
    }

    override suspend fun getLocationDetail(locationId: Int): Result<LocationDetail> {
        return pokemonRemoteDataSource.getLocationDetail(locationId)
    }

    override suspend fun getAreaDetail(areaId: Int): Result<AreaDetail> {
        return pokemonRemoteDataSource.getAreaDetail(areaId)
    }

    override suspend fun getSpecie(specieId: Int): Result<Specie> {
        return pokemonRemoteDataSource.getSpecie(specieId)
    }

    override suspend fun pokemonCatch(token: String, pokemonCatch: PokemonCatch): Result<CapturedPokemon> {
        return pokemonRemoteDataSource.pokemonCatch(token, pokemonCatch)
    }

    override suspend fun getPokemonParty(token: String): Result<List<UserPokemon>> {
        return pokemonRemoteDataSource.getPokemonParty(token)
    }

    override suspend fun getPokemonStorage(token: String): Result<List<UserPokemon>> {
        return pokemonRemoteDataSource.getPokemonStorage(token)
    }

    override suspend fun pokemonRename(
        id: Int,
        token: String,
        pokemonRename: PokemonRename
    ): Result<CapturedPokemon> {
        return pokemonRemoteDataSource.pokemonRename(id, token, pokemonRename)
    }

    override suspend fun pokemonRelease(id: Int, token: String): Result<String> {
        return pokemonRemoteDataSource.pokemonRelease(id, token)
    }

    override suspend fun swapPartyMember(token: String, swapPartyMember: SwapPartyMember): Result<List<UserPokemon>> {
        return pokemonRemoteDataSource.swapPartyMember(token, swapPartyMember)
    }

    override fun getTypeIcon(typeName: String): Int {
        return pokemonRemoteDataSource.getTypeIcon(typeName)
    }

    override fun getPokemonTypes(): List<PokemonType> {
        return pokemonRemoteDataSource.getPokemonTypes()
    }

}