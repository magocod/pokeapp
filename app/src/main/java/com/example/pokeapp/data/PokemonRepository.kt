package com.example.pokeapp.data

import android.util.Log
import com.example.pokeapp.network.*
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class PokemonRepository {

    suspend fun getRegions(): Result<List<Region>> {
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

    suspend fun getRegionDetail(regionId: Int): Result<RegionDetail> {
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

    suspend fun getLocationDetail(locationId: Int): Result<LocationDetail> {
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

    suspend fun getAreaDetail(areaId: Int): Result<AreaDetail> {
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

    suspend fun pokemonCatch(token: String, pokemonCatch: PokemonCatch): Result<CapturedPokemon> {
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
}