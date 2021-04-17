package com.example.pokeapp.data

import android.util.Log
import com.example.pokeapp.R
import com.example.pokeapp.data.model.PokemonType
import com.example.pokeapp.network.*
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class PokemonRemoteDataSource : PokeRepository {

    override suspend fun getRegions(): Result<List<Region>> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getRegions()
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo regions", e.toString());
            Result.Error(IOException("Error loading regions", e))
        }
    }

    override suspend fun getRegionDetail(regionId: Int): Result<RegionDetail> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getRegionDetail(regionId)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }
//            Log.d("PokeRepo region", response.body().toString());

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo region", e.toString());
            Result.Error(IOException("Error loading region", e))
        }
    }

    override suspend fun getLocationDetail(locationId: Int): Result<LocationDetail> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getLocationDetail(locationId)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }
//            Log.d("PokeRepo location", response.body().toString());

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo location", e.toString());
            Result.Error(IOException("Error loading location", e))
        }
    }

    override suspend fun getAreaDetail(areaId: Int): Result<AreaDetail> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getAreaDetail(areaId)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }
//            Log.d("PokeRepo area", response.body().toString());

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo area", e.toString());
            Result.Error(IOException("Error loading area", e))
        }
    }

    override suspend fun getSpecie(specieId: Int): Result<Specie> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getSpecie(specieId)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }
//            Log.d("PokeRepo area", response.body().toString());

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo specie", e.toString());
            Result.Error(IOException("Error loading specie", e))
        }
    }

    override suspend fun pokemonCatch(
        token: String,
        pokemonCatch: PokemonCatch,
    ): Result<CapturedPokemon> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.pokemonCatch(makeAuthHeader(token), pokemonCatch)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }
//            Log.d("PokeRepo area", response.body().toString());

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo catch", e.toString());
            Result.Error(IOException("Error catch", e))
        }
    }

    override suspend fun getPokemonParty(token: String): Result<List<UserPokemon>> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getPokemonParty(makeAuthHeader(token))
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo party", e.toString());
            Result.Error(IOException("Error loading party", e))
        }
    }

    override suspend fun getPokemonStorage(token: String): Result<List<UserPokemon>> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.getPokemonStorage(makeAuthHeader(token))
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo storage", e.toString());
            Result.Error(IOException("Error loading storage", e))
        }
    }

    override suspend fun pokemonRename(
        id: Int,
        token: String,
        pokemonRename: PokemonRename,
    ): Result<CapturedPokemon> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.pokemonRename(id, makeAuthHeader(token), pokemonRename)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            if (e is HttpException) {
                Log.d("PokeRepo http e", e.response()!!.code().toString());
                Log.d("PokeRepo http e", e.response()!!.body().toString());
                Log.d("PokeRepo http e", e.response()!!.errorBody().toString());
            }
            Log.d("PokeRepo rename", e.toString());
            Result.Error(IOException(e.toString(), e))
        }
    }

    override suspend fun pokemonRelease(id: Int, token: String): Result<String> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.pokemonRelease(id, makeAuthHeader(token))
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success("void")
        } catch (e: Throwable) {
            Log.d("PokeRepo release", e.toString());
            Result.Error(IOException(e.toString(), e))
        }
    }

    override suspend fun swapPartyMember(
        token: String,
        swapPartyMember: SwapPartyMember,
    ): Result<List<UserPokemon>> {
        return try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.swapPartyMember(makeAuthHeader(token), swapPartyMember)
            }

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            Result.Success(response.body()!!)
        } catch (e: Throwable) {
            Log.d("PokeRepo swap", e.toString());
            Result.Error(IOException(e.toString(), e))
        }
    }

    override fun getTypeIcon(typeName: String): Int {
        val pokemonType = getPokemonTypes().find {
            it.name == typeName
        }
        if (pokemonType != null) {
            return pokemonType.drawable
        }
        return R.drawable.ic_broken_image
    }

    override fun getPokemonTypes(): List<PokemonType> {
        return listOf(
            PokemonType(
                1,
                "steel",
                R.drawable.type_steel
            ),
            PokemonType(
                2,
                "fire",
                R.drawable.type_fire
            ),
            PokemonType(
                3,
                "fighting",
                R.drawable.type_fighting
            ),
            PokemonType(
                4,
                "fairy",
                R.drawable.type_fairy
            ),
            PokemonType(
                5,
                "ice",
                R.drawable.type_ice
            ),
            PokemonType(
                6,
                "grass",
                R.drawable.type_grass
            ),
            PokemonType(
                7,
                "bug",
                R.drawable.type_bug
            ),
            PokemonType(
                8,
                "normal",
                R.drawable.type_normal
            ),
            PokemonType(
                9,
                "electric",
                R.drawable.type_electric
            ),
            PokemonType(
                10,
                "ground",
                R.drawable.type_ground
            ),
            PokemonType(
                11,
                "psychic",
                R.drawable.type_psychic
            ),
            PokemonType(
                12,
                "rock",
                R.drawable.type_rock
            ),
            PokemonType(
                13,
                "ghost",
                R.drawable.type_ghost
            ),
            PokemonType(
                14,
                "water",
                R.drawable.type_water
            ),
            PokemonType(
                15,
                "flying",
                R.drawable.type_flying
            ),
            PokemonType(
                16,
                "poison",
                R.drawable.type_poison
            ),
            PokemonType(
                17,
                "dragon",
                R.drawable.type_dragon
            ),
            PokemonType(
                18,
                "dark",
                R.drawable.type_dark
            )
        )
    }

}