package com.example.pokeapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pokeapp.R
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val loginViewModel: LoginViewModel by activityViewModels { LoginViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            Snackbar.make(view, "Capture Pokemon", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_global_regionFragment)
        }

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d("homefragment login", it.toString())
                if (it) {
                    fab.visibility = View.VISIBLE
                } else {
                    // pass
//                    fab.visibility = View.GONE
                }
            })
    }
}