package com.example.pokeapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val AUTH_KEY = "Token"
private const val BASE_URL = "http://10.0.2.2:8000"
//private const val BASE_URL = "https://androvirtual12.pythonanywhere.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//val okHttpClient: OkHttpClient = OkHttpClient.Builder()
//    .addInterceptor(AuthNetworkInterceptor())
//    .build()

private val retrofit = Retrofit.Builder()
//    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
    .baseUrl(BASE_URL)
    .build()

//class AuthNetworkInterceptor: Interceptor {
//    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//        Log.d("request http before", "ok_http");
//        val request = chain.request()
//        val response = chain.proceed(request);
//        Log.d("request http after", "ok_http");
//        return response
//    }
//}

interface PokemonApiService {
    // auth
    @Headers("Content-Type: application/json")
    @POST("login/")
    suspend fun login(@Body usernameCredential: UsernameCredential): Response<BasicToken>

    @POST("logout/")
    suspend fun logout(@Header("Authorization") token: String): Response<Void>

    @GET("regions")
    suspend fun getRegions(): Response<List<Region>>

    @GET("regions/{id}")
    suspend fun getRegionDetail(@Path("id") id: Int): Response<RegionDetail>

    @GET("location/{id}")
    suspend fun getLocationDetail(@Path("id") id: Int): Response<LocationDetail>

    @GET("areas/{id}")
    suspend fun getAreaDetail(@Path("id") id: Int): Response<AreaDetail>

    @GET("pokemons/{id}")
    suspend fun getSpecie(@Path("id") id: Int): Response<Specie>

    @POST("pokemons/own/")
    suspend fun pokemonCatch(
        @Header("Authorization") token: String,
        @Body pokemonCatch: PokemonCatch,
    ): Response<CapturedPokemon>

    @GET("pokemons/own/party/")
    suspend fun getPokemonParty(@Header("Authorization") token: String): Response<List<UserPokemon>>

    @GET("pokemons/own/")
    suspend fun getPokemonStorage(@Header("Authorization") token: String): Response<List<UserPokemon>>

    @PUT("pokemons/own/{id}/")
    suspend fun pokemonRename(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body pokemonRename: PokemonRename,
    ): Response<CapturedPokemon>

    @DELETE("pokemons/own/{id}/")
    suspend fun pokemonRelease(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Response<Void>

    @POST("pokemons/own/swap/")
    suspend fun swapPartyMember(
        @Header("Authorization") token: String,
        @Body pokemonCatch: SwapPartyMember,
    ): Response<List<UserPokemon>>
}

/**
 * add required word in authentication header
 */
fun makeAuthHeader(token: String): String {
    return "$AUTH_KEY $token"
}

object PokemonApi {

    val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}