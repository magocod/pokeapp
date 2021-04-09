package com.example.pokeapp.ui.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pokeapp.R
import com.example.pokeapp.data.Result
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

class SpecieFragment : Fragment() {

    companion object {
        fun newInstance() = SpecieFragment()


    }

    private lateinit var viewModel: SpecieViewModel

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    val args: SpecieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.specie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SpecieViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPokemon(view, "---")
        setImgPokemon(view, null)
        view.findViewById<ImageView>(R.id.type_1).visibility = View.GONE
        view.findViewById<ImageView>(R.id.type_2).visibility = View.GONE

        pokemonViewModel.getSpecie(args.specieId)

        pokemonViewModel.specie.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                Log.d("captured poke s", result.toString())
                if (result is Result.Success) {
                    setPokemon(view, result.data.name)
                    setTypes(view, result.data.types)
                    setImgPokemon(view, result.data.sprites.frontDefault)
                }
            })
    }

    private fun setPokemon(view: View, pokemonName: String) {
        view.findViewById<TextView>(R.id.pokemon_name).text = pokemonName
    }

    private fun setTypes(view: View, types: List<String>) {
        val imageViewTypeA = view.findViewById<ImageView>(R.id.type_1)
        val imageViewTypeB = view.findViewById<ImageView>(R.id.type_2)

        if (types.size == 2) {
            imageViewTypeA.setImageResource(pokemonViewModel.getTypeIcon(types[0]))
            imageViewTypeB.setImageResource(pokemonViewModel.getTypeIcon(types[1]))
            imageViewTypeA.visibility = View.VISIBLE
            imageViewTypeB.visibility = View.VISIBLE
        } else if (types.size == 1) {
            imageViewTypeA.setImageResource(pokemonViewModel.getTypeIcon(types[0]))
            imageViewTypeA.visibility = View.VISIBLE
            imageViewTypeB.visibility = View.GONE
        }
    }

    private fun setImgPokemon(view: View, imgSrc: String? = null) {
        if (imgSrc != null) {
            val imgUri = imgSrc.toUri().buildUpon().scheme("https").build()
            view.findViewById<ImageView>(R.id.imageView3).load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        } else {
            view.findViewById<ImageView>(R.id.imageView3).setImageResource(R.drawable.ic_broken_image)
        }
    }

}