package com.example.pokeapp.ui.region

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
import com.example.pokeapp.network.Location
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class RegionDetailFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    val args: RegionDetailFragmentArgs by navArgs()

    private var columnCount = 1

    private lateinit var regionId: String

    private var recyclerView: RegionDetailRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//            regionId = it.getString(REGION_ID).toString()
//            Log.d("RegionD navigation", regionId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_region_detail_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    RegionDetailRecyclerViewAdapter(mutableListOf(Location(1, "loading error")))
            }
            recyclerView = view.adapter as RegionDetailRecyclerViewAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RegionD navigation", args.regionId.toString())
        pokemonViewModel.getRegionDetail(args.regionId)

        pokemonViewModel.locations.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { locations ->
//                Log.d("pokeVM locations", locations.toString())
                recyclerView.let {
                    it!!.updateData(locations)
                }
            })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val REGION_ID = "regionId"

//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int, regionId: Int) =
//            RegionDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                    putInt(REGION_ID, regionId)
//                }
//            }
    }
}