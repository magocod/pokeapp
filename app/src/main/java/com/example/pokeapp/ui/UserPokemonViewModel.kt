package com.example.pokeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.PokemonRepository
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.CapturedPokemon
import com.example.pokeapp.network.PokemonRename
import com.example.pokeapp.network.SwapPartyMember
import com.example.pokeapp.network.UserPokemon
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserPokemonViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _team = MutableLiveData<List<UserPokemon>>()
    val team: LiveData<List<UserPokemon>> = _team

    private val _storage = MutableLiveData<List<UserPokemon>>()
    val storage: LiveData<List<UserPokemon>> = _storage

    private val _capturedPokemon = MutableLiveData<Result<CapturedPokemon>?>()
    val capturedPokemon: LiveData<Result<CapturedPokemon>?> = _capturedPokemon

    private val _release = MutableLiveData<Result<String>?>()
    val release: LiveData<Result<String>?> = _release

    private val _swap = MutableLiveData<Result<List<UserPokemon>>?>()
    val swap: LiveData<Result<List<UserPokemon>>?> = _swap

    /**
     * Show a loading spinner if true
     */
    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Request a snackbar to display a string.
     *
     * This variable is private because we don't want to expose MutableLiveData
     *
     * MutableLiveData allows anyone to set a value, and MainViewModel is the only
     * class that should be setting values.
     */
    private val _snackBar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String?>
        get() = _snackBar

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

    fun pokemonRename(id: Int, token: String, pokemonRename: PokemonRename) {
        viewModelScope.launch {
            _spinner.value = true
            val result = pokemonRepository.pokemonRename(id, token, pokemonRename)
            _capturedPokemon.value = result
//            if (result is Result.Error) {
//                _snackBar.value = result.toString()
//            }
            _spinner.value = false
        }
    }

    fun pokemonRelease(id: Int, token: String) {
        viewModelScope.launch {
            _spinner.value = true
            val result = pokemonRepository.pokemonRelease(id, token)
            _release.value = result
            if (result is Result.Success) {
//                _team.value = result.data
            } else {
                _team.value = listOf()
                _snackBar.value = result.toString()
            }
            _spinner.value = false
        }
    }

    fun swapPartyMember(token: String, swapPartyMember: SwapPartyMember) {
        viewModelScope.launch {
            _spinner.value = true
            val result = pokemonRepository.swapPartyMember(token, swapPartyMember)
            _swap.value = result
            if (result is Result.Success) {
                _team.value = result.data
            }
            _spinner.value = false
        }
    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun onCapturedShown() {
        _capturedPokemon.value = null
    }

    fun onReleaseShown() {
        _release.value = null
    }

    fun onSwapShown() {
        _swap.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Exception) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}