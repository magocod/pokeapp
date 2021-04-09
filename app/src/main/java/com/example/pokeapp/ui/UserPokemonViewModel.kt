package com.example.pokeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.PokemonRepository
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.UserPokemon
import kotlinx.coroutines.launch

class UserPokemonViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _team = MutableLiveData<List<UserPokemon>>()
    val team: LiveData<List<UserPokemon>> = _team

    private val _storage = MutableLiveData<List<UserPokemon>>()
    val storage: LiveData<List<UserPokemon>> = _storage

    init {
        _storage.value = listOf()
    }

    fun getPokemonParty(token: String) {
        viewModelScope.launch {
            val result = pokemonRepository.getPokemonParty(token)
            if (result is Result.Success) {
                _team.value = result.data
            } else {
                _team.value = listOf()
            }
        }
    }

    fun getPokemonStorage(token: String) {
        viewModelScope.launch {
            val result = pokemonRepository.getPokemonStorage(token)
            if (result is Result.Success) {
                _storage.value = result.data
            } else {
                _storage.value = listOf()
            }
        }
    }
}