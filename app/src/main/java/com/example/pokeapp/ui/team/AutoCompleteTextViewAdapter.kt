package com.example.pokeapp.ui.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.pokeapp.R
import com.example.pokeapp.network.Move

class AutoCompleteTextViewAdapter(private val c: Context, @LayoutRes private val layoutResource: Int, private val movies: Array<Move>) :
    ArrayAdapter<Move>(c, layoutResource, movies) {

    var filteredMovies: List<Move> = listOf()

    override fun getCount(): Int = filteredMovies.size

    override fun getItem(position: Int): Move = filteredMovies[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(layoutResource, parent, false)

        if (view == null) {
            return view
        }

//        view.findViewById<TextView>(R.id.tvMovieName)
//        view.findViewById<TextView>(R.id.tvMovieYear)

        // autocomplete_custom
        view.findViewById<TextView>(R.id.content_1).text = filteredMovies[position].name
        view.findViewById<TextView>(R.id.content_2).text = filteredMovies[position].name

        // autocomplete_custom_2


        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredMovies = filterResults.values as List<Move>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    movies.asList()
                else
                    movies.filter {
                        it.name.toLowerCase().contains(queryString) || it.name.toString().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}