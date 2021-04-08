package com.example.pokeapp.ui.location

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.network.Area
import com.example.pokeapp.ui.location.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class LocationRecyclerViewAdapter(
    private val values: MutableList<Area>
) : RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.name
        val newText = "pokemons: ${item.pokemonCount.toString()}"
        holder.thirdContentView.text = newText
        holder.itemView.setOnClickListener() {
            Log.d("LocationRecycler", "position: $position id: ${item.id}")
            val navController = Navigation.findNavController(holder.itemView)
            val action = LocationFragmentDirections.actionLocationFragmentToCapturePokemonFragment(
                areaId = item.id,
                areaName = item.name
            )
//            navController.navigate(R.id.action_locationFragment_to_capturePokemonFragment)
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val thirdContentView: TextView = view.findViewById(R.id.third_content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun updateData(areas: List<Area>) {
        values.clear()
        values.addAll(areas)
        notifyDataSetChanged()
//        Log.d("recycler update", values.toString())
    }
}