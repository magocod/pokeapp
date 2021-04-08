package com.example.pokeapp.ui.location

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
import com.example.pokeapp.network.Area
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class LocationFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    val args: LocationFragmentArgs by navArgs()

    private var columnCount = 1

    private var recyclerView: LocationRecyclerViewAdapter? = null

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
        val view = inflater.inflate(R.layout.fragment_location_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = LocationRecyclerViewAdapter(
                    mutableListOf(Area(1, "error loading", 1, 1))
                )
            }
            recyclerView = view.adapter as LocationRecyclerViewAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LocationD navigation", args.locationId.toString())
        pokemonViewModel.getLocationDetail(args.locationId)

        pokemonViewModel.areas.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { areas ->
//                Log.d("pokeVM locations", locations.toString())
                recyclerView.let {
                    it!!.updateData(areas)
                }
            })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            LocationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}