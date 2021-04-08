package com.example.pokeapp.ui.region

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.network.Location
import com.example.pokeapp.ui.region.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RegionDetailRecyclerViewAdapter(
    private val values: MutableList<Location>
) : RecyclerView.Adapter<RegionDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_region_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.name
        holder.itemView.setOnClickListener() {
            Log.d("RegionDetailRecycler", "position: $position id: ${item.id}")
            val navController = Navigation.findNavController(holder.itemView)
            val action =
                RegionDetailFragmentDirections.actionRegionDetailFragmentToLocationFragment(
                    locationId = item.id,
                    locationName = item.name
                )
//            navController.navigate(R.id.action_regionDetailFragment_to_locationFragment)
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

    fun updateData(locations: List<Location>) {
        values.clear()
        values.addAll(locations)
        notifyDataSetChanged()
//        Log.d("recycler update", values.toString())
    }
}