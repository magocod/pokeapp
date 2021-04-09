package com.example.pokeapp.ui.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokeapp.R
import com.example.pokeapp.data.Result
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PokeStatusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokeStatusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poke_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusPokemon(
            view,
            "---",
            "---",
            "---"
        )

        pokemonViewModel.specie.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                Log.d("captured poke s", result.toString())
                if (result is Result.Success) {
                    if (result.data.stats.size > 3) {
                        setStatusPokemon(
                            view,
                            "${result.data.stats[0].name}: ${result.data.stats[0].value}",
                            "${result.data.stats[1].name}: ${result.data.stats[1].value}",
                            "${result.data.stats[2].name}: ${result.data.stats[2].value}",
                        )
                    } else {
                        setStatusPokemon(
                            view,
                            "---",
                            "---",
                            "---"
                        )
                    }
                }
            })
    }

    private fun setStatusPokemon(view: View, statusA: String, statusB: String, statusC: String) {
        view.findViewById<TextView>(R.id.status_1).text = statusA
        view.findViewById<TextView>(R.id.status_2).text = statusB
        view.findViewById<TextView>(R.id.status_3).text = statusC
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PokeStatusFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PokeStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}