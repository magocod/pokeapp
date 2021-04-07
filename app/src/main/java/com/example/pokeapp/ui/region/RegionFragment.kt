package com.example.pokeapp.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.network.Region
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class RegionFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }
    private var recyclerView : RegionRecyclerViewAdapter? = null

    private var columnCount = 1

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
        val view = inflater.inflate(R.layout.fragment_region_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = RegionRecyclerViewAdapter(mutableListOf(Region(1, "error loading")))
//                recyclerView = adapter
            }
            recyclerView = view.adapter as RegionRecyclerViewAdapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonViewModel.getRegions()

        pokemonViewModel.regions.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { regions ->
//                Log.d("pokeVM regions", regions.toString())
                recyclerView.let {
//                    val mList = mutableListOf<Region>()
//                    mList.addAll(regions)
                    it!!.updateData(regions)
                }
            })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RegionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}