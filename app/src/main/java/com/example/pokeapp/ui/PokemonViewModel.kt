package com.example.pokeapp.ui

import androidx.lifecycle.*
import com.example.pokeapp.data.PokemonRepository
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.*
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _regions = MutableLiveData<List<Region>>()
    val regions: LiveData<List<Region>> = _regions

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    private val _areas = MutableLiveData<List<Area>>()
    val areas: LiveData<List<Area>> = _areas

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _capturedPokemon = MutableLiveData<Result<CapturedPokemon>>()
    val capturedPokemon: LiveData<Result<CapturedPokemon>> = _capturedPokemon

    private val _specie = MutableLiveData<Result<Specie>>()
    val specie: LiveData<Result<Specie>> = _specie

    private val _specieDetail = MutableLiveData<Specie>()
    private val specieDetail: LiveData<Specie> = _specieDetail

    val specieMoves = Transformations.map(specieDetail) { specie ->
        specie.moves.mapIndexed { index, value ->
            Move(index, value)
        }
    }


    init {
        _regions.value = listOf()
        _locations.value = listOf()
        _areas.value = listOf()
        _pokemons.value = listOf()
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

    fun getLocationDetail(locationId: Int) {
        viewModelScope.launch {
            val result = pokemonRepository.getLocationDetail(locationId)
            if (result is Result.Success) {
                _areas.value = result.data.areas
            } else {
                _areas.value = listOf()
            }
        }
    }

    fun getAreaDetail(areaId: Int) {
        viewModelScope.launch {
            val result = pokemonRepository.getAreaDetail(areaId)
            if (result is Result.Success) {
                _pokemons.value = result.data.pokemons
            } else {
                _pokemons.value = listOf()
            }
        }
    }

    fun pokemonCatch(token: String, pokemonCatch: PokemonCatch) {
        viewModelScope.launch {
            val result = pokemonRepository.pokemonCatch(token, pokemonCatch)
            _capturedPokemon.value = result
        }
    }

    fun getSpecie(specieId: Int) {
        viewModelScope.launch {
            val result = pokemonRepository.getSpecie(specieId)
            _specie.value = result
            if (result is Result.Success) {
                _specieDetail.value = result.data
            }
        }
    }

    fun getTypeIcon(typeName: String): Int {
        return pokemonRepository.getTypeIcon(typeName)
    }
}