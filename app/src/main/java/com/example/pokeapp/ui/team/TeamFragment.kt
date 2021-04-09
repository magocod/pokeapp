package com.example.pokeapp.ui.team

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
import com.example.pokeapp.ui.UserPokemonViewModel
import com.example.pokeapp.ui.UserPokemonViewModelFactory
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class TeamFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels { LoginViewModelFactory() }
    private val userPokemonViewModel: UserPokemonViewModel by activityViewModels { UserPokemonViewModelFactory() }

    private var columnCount = 1

    private var recyclerView: TeamRecyclerViewAdapter? = null

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
        val view = inflater.inflate(R.layout.fragment_team_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = TeamRecyclerViewAdapter(mutableListOf())
            }
            recyclerView = view.adapter as TeamRecyclerViewAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = loginViewModel.getToken()
        if (token != null) {
            userPokemonViewModel.getPokemonParty(token)
        }

//        loginViewModel.login("u", "p")
//        loginViewModel.isLoggedIn.observe(viewLifecycleOwner,
//            androidx.lifecycle.Observer { isLoggedIn ->
//                Log.d("captured isLoggedIn", isLoggedIn.toString())
//                if (isLoggedIn) {
//                    val tk = loginViewModel.getToken()
//                    if (tk != null) {
//                        userPokemonViewModel.getPokemonParty(tk)
//                    }
//                } else {
//                    // pass
//                }
//            })

        userPokemonViewModel.team.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { team ->
//                Log.d("pokeVM locations", locations.toString())
                recyclerView.let {
                    it!!.updateData(team)
                }
            })

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TeamFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}