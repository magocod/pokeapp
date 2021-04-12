package com.example.pokeapp.ui.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import coil.load
import com.example.pokeapp.R
import com.example.pokeapp.network.UserPokemon
import java.util.*


class SwapPokemonAdapter(
    private val c: Context,
    @LayoutRes private val layoutResource: Int,
    private val movies: Array<UserPokemon>,
) :
    ArrayAdapter<UserPokemon>(c, layoutResource, movies) {

    var filteredMovies: List<UserPokemon> = listOf()

    override fun getCount(): Int = filteredMovies.size

    override fun getItem(position: Int): UserPokemon = filteredMovies[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(layoutResource, parent, false)

        if (view == null) {
            return view
        }

//        view.findViewById<TextView>(R.id.tvMovieName)
//        view.findViewById<TextView>(R.id.tvMovieYear)

        val pokemon = filteredMovies[position]

        // autocomplete_custom
//        view.findViewById<TextView>(R.id.content_1).text = filteredMovies[position].nickName
//        view.findViewById<TextView>(R.id.content_2).text = filteredMovies[position].specie.name

        // autocomplete_custom_2
//        val contentB = "specie: ${pokemon.specie.name}"
        view.findViewById<TextView>(R.id.content_1).text = pokemon.nickName
        view.findViewById<TextView>(R.id.content_2).text = pokemon.specie.name

        pokemon.specie.sprites.frontDefault?.let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()
            view.findViewById<ImageView>(R.id.imageView).load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredMovies = filterResults.values as List<UserPokemon>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.ROOT)

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    movies.asList()
                else
                    movies.filter {
                        it.nickName.toLowerCase(Locale.ROOT)
                            .contains(queryString) || it.specie.name.toLowerCase(
                            Locale.ROOT).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}