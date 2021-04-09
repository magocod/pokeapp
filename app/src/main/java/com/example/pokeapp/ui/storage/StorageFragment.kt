package com.example.pokeapp.ui.storage

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A fragment representing a list of Items.
 */
class StorageFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels { LoginViewModelFactory() }
    private val userPokemonViewModel: UserPokemonViewModel by activityViewModels { UserPokemonViewModelFactory() }

    private var columnCount = 2

    private var recyclerView: StorageRecyclerViewAdapter? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_storage_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = StorageRecyclerViewAdapter(mutableListOf())
            }
            recyclerView = view.adapter as StorageRecyclerViewAdapter
        }

        return view
//        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.apply {
//            // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
//            lifecycleOwner = viewLifecycleOwner
//
//            viewModel = userPokemonViewModel
//            storageFragment = this@StorageFragment
//        }

        val token = loginViewModel.getToken()
        if (token != null) {
            userPokemonViewModel.getPokemonStorage(token)
        }

        loginViewModel.login("u", "p")
        loginViewModel.isLoggedIn.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { isLoggedIn ->
                Log.d("captured isLoggedIn", isLoggedIn.toString())
                if (isLoggedIn) {
                    val tk = loginViewModel.getToken()
                    if (tk != null) {
                        userPokemonViewModel.getPokemonStorage(tk)
                    }
                } else {
                    // pass
                }
            })

        userPokemonViewModel.storage.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { storage ->
//                Log.d("pokeVM locations", locations.toString())
                recyclerView.let {
                    it!!.updateData(storage)
                }
            })

    }

    fun showDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.temporary_title)
            .setMessage(R.string.temporary_message)
            .setCancelable(true)
            .setNegativeButton(R.string.cancel) { _, _ ->
                // pass
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                // pass
            }
            .show()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            StorageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}