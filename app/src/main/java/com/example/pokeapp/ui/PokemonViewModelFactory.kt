package com.example.pokeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.data.PokemonRemoteDataSource
import com.example.pokeapp.data.PokemonRepository

class PokemonViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(
                pokemonRepository = PokemonRepository(PokemonRemoteDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
