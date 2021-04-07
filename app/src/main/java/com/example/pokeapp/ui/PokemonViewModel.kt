package com.example.pokeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.PokemonRepository
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.Location
import com.example.pokeapp.network.Region
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _regions = MutableLiveData<List<Region>>()
    val regions: LiveData<List<Region>> = _regions

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    init {
        _regions.value = listOf()
        _locations.value = listOf()
    }

    fun getRegions() {
        viewModelScope.launch {
            val result = pokemonRepository.getRegions()
            if (result is Result.Success) {
                _regions.value = result.data
            } else {
                _regions.value = listOf()
            }
        }
    }

    fun getRegionDetail(regionId: Int) {
        viewModelScope.launch {
            val result = pokemonRepository.getRegionDetail(regionId)
            if (result is Result.Success) {
                _locations.value = result.data.locations
            } else {
                _locations.value = listOf()
            }
        }
    }
}