package com.example.pokeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.data.PokemonRepository

class UserPokemonViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPokemonViewModel::class.java)) {
            return UserPokemonViewModel(
                pokemonRepository = PokemonRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}