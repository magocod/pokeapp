package com.example.pokeapp.data

import android.util.Log
import com.example.pokeapp.network.PokemonApi
import com.example.pokeapp.network.Region
import com.example.pokeapp.network.RegionDetail
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
}