package com.example.pokeapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val AUTH_KEY = "Token"
private const val BASE_URL = "http://10.0.2.2:8000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//val okHttpClient: OkHttpClient = OkHttpClient.Builder()
//    .addInterceptor(AuthNetworkInterceptor())
//    .build()

private val retrofit = Retrofit.Builder()
//    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
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

    // regions

    @GET("regions")
    suspend fun getRegions(): List<Region>

    @GET("regions/{id}")
    suspend fun getRegionDetail(@Path("id") id: Int): Region
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