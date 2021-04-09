package com.example.pokeapp.ui.team

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokeapp.R
import com.example.pokeapp.network.UserPokemon
import com.example.pokeapp.ui.team.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class TeamRecyclerViewAdapter(
    private val values: MutableList<UserPokemon>
) : RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_team, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.nickName
        holder.thirdContentView.text = item.specie.name

        item.specie.sprites.frontDefault?.let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()
            holder.imageView.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }

        holder.itemView.setOnClickListener() {
            Log.d("TeamRecycler", "position: $position id: ${item.id}")
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.action_nav_team_to_specieFragment)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val thirdContentView: TextView = view.findViewById(R.id.third_content)
        val imageView: ImageView = view.findViewById(R.id.mtrl_list_item_icon)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun updateData(items: List<UserPokemon>) {
        values.clear()
        values.addAll(items)
        notifyDataSetChanged()
//        Log.d("recycler update", values.toString())
    }
}