package com.example.pokeapp.ui.region

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.network.Region
import com.example.pokeapp.ui.region.dummy.DummyContent.DummyItem


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RegionRecyclerViewAdapter(
    private val values: MutableList<Region>
) : RecyclerView.Adapter<RegionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.name
        holder.itemView.setOnClickListener() {
            Log.d("RegionRecycler", "position: $position id: ${item.id}")
            val navController = Navigation.findNavController(holder.itemView)
            val action = RegionFragmentDirections.actionRegionFragmentToRegionDetailFragment(
                regionId = item.id,
                regionName = item.name
            )
//            navController.navigate(R.id.action_regionFragment_to_regionDetailFragment)
            navController.navigate(action)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun updateData(regions: List<Region>) {
        values.clear()
        values.addAll(regions)
        notifyDataSetChanged()
//        Log.d("recycler update", values.toString())
    }
}