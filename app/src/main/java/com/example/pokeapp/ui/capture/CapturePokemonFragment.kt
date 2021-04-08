package com.example.pokeapp.ui.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.network.Pokemon
import com.example.pokeapp.network.Sprites
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class CapturePokemonFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    val args: CapturePokemonFragmentArgs by navArgs()

    private var columnCount = 1

    private var recyclerView: CapturePokemonRecyclerViewAdapter? = null

    private val newCaptureActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_capture_pokemon_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    CapturePokemonRecyclerViewAdapter(
                        mutableListOf(
                            Pokemon(
                                1,
                                "error loading",
                                sprites = Sprites(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/183.png",
                                    "",
                                    "",
                                    ""
                                )
                            )
                        )
                    )
            }
            recyclerView = view.adapter as CapturePokemonRecyclerViewAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("areaD navigation", args.areaId.toString())
        pokemonViewModel.getAreaDetail(args.areaId)

        pokemonViewModel.pokemons.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { pokemons ->
//                Log.d("pokeVM locations", locations.toString())
                recyclerView.let {
                    it!!.updateData(pokemons)
                }
            })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CapturePokemonFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}