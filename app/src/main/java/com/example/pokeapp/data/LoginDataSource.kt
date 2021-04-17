package com.example.pokeapp.data

import android.util.Log
import com.example.pokeapp.data.model.LoggedInUser
import com.example.pokeapp.network.PokemonApi
import com.example.pokeapp.network.UsernameCredential
import com.example.pokeapp.network.makeAuthHeader
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.util.*


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.login(
                    UsernameCredential("basic_user", "123")
                )
            }

            Log.d("LoginResource code", response.code().toString());

//            val raw = response.errorBody()
//            val gson = Gson()
//            val message: Any = gson.fromJson(
//                response.errorBody()!!.charStream(),
//                Any::class.java
//            )
//            Log.d("LoginResource body", response.raw().toString());

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            val fakeUser = LoggedInUser(
                UUID.randomUUID().toString(),
                "Trainer",
                response.body()!!.token
            )
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            Log.d("LoginResource catch", e.toString());
            return Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun logout(token: String): Result<Boolean> {
        try {
            val response = withTimeout(5_000) {
                PokemonApi.retrofitService.logout(makeAuthHeader(token))
            }

            Log.d("LogoutResource code", response.code().toString());

            if (!response.isSuccessful) {
                throw HttpException(response) // or handle - whatever
            }

            return Result.Success(true)
        } catch (e: Throwable) {
            Log.d("LogoutResource catch", e.toString());
            return Result.Error(IOException("Error logging in", e))
        }
    }
}