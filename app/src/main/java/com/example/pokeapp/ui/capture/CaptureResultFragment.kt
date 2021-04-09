package com.example.pokeapp.ui.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pokeapp.R
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.CapturedPokemon
import com.example.pokeapp.network.PokemonCatch
import com.example.pokeapp.ui.PokemonViewModel
import com.example.pokeapp.ui.PokemonViewModelFactory
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CaptureResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CaptureResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val loginViewModel: LoginViewModel by activityViewModels { LoginViewModelFactory() }
    private val pokemonViewModel: PokemonViewModel by activityViewModels() { PokemonViewModelFactory() }

    val args: CaptureResultFragmentArgs by navArgs()

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
        return inflater.inflate(R.layout.fragment_capture_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonCatch = PokemonCatch(
            args.specieId,
            args.nickName,
            args.isPartyMember,
        )
        Log.d("captured args", pokemonCatch.toString())
//        loginViewModel.getToken().let {
//            pokemonViewModel.pokemonCatch(it!!, pokemonCatch)
//        }

        setCapturedPokemon(view, null)
        setImgPokemon(view, null)

        view.findViewById<Button>(R.id.confirm_btn).setOnClickListener() {
            val token = loginViewModel.getToken()
            if (token != null) {
                pokemonViewModel.pokemonCatch(token!!, pokemonCatch)
            }
        }

        view.findViewById<Button>(R.id.team_btn).setOnClickListener() {
            findNavController().navigate(R.id.action_captureResultFragment_to_nav_team)
        }

        view.findViewById<Button>(R.id.storage_btn).setOnClickListener() {
            findNavController().navigate(R.id.action_captureResultFragment_to_nav_storage)
        }

        view.findViewById<Button>(R.id.back_btn).setOnClickListener() {
            findNavController().popBackStack()
        }

//        val debugLoginButton = view.findViewById<Button>(R.id.debug_login)
//        debugLoginButton.setOnClickListener() {
//            loginViewModel.login("u", "p")
//        }

//        loginViewModel.isLoggedIn.observe(viewLifecycleOwner,
//            androidx.lifecycle.Observer { isLoggedIn ->
//                Log.d("captured isLoggedIn", isLoggedIn.toString())
//                if (isLoggedIn) {
//                    debugLoginButton.visibility = View.GONE
//                } else {
//                    debugLoginButton.visibility = View.VISIBLE
//                }
//            })

        pokemonViewModel.capturedPokemon.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                Log.d("captured poke", result.toString())
                if (result is Result.Success) {
                    setCapturedPokemon(view, result.data)
                    setImgPokemon(view, null)
                    pokemonViewModel.getSpecie(result.data.specie)
                }
            })

        pokemonViewModel.specie.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { result ->
                Log.d("captured poke s", result.toString())
                if (result is Result.Success) {
                    setImgPokemon(view, result.data.sprites.frontDefault)
                }
            })
    }

    private fun setCapturedPokemon(view: View, capturedPokemon: CapturedPokemon? = null) {
        if (capturedPokemon != null) {
            view.findViewById<TextView>(R.id.item_number).text = capturedPokemon.id.toString()
            view.findViewById<TextView>(R.id.content).text = capturedPokemon.nickName
            view.findViewById<SwitchMaterial>(R.id.content_switch).isChecked =
                capturedPokemon.isPartyMember
        } else {
            view.findViewById<TextView>(R.id.item_number).text = ""
            view.findViewById<TextView>(R.id.content).text = ""
            view.findViewById<SwitchMaterial>(R.id.content_switch).isChecked = false
        }
    }

    private fun setImgPokemon(view: View, imgSrc: String? = null) {
        if (imgSrc != null) {
            val imgUri = imgSrc.toUri().buildUpon().scheme("https").build()
            view.findViewById<ImageView>(R.id.item_image).load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        } else {
            view.findViewById<ImageView>(R.id.item_image).setImageResource(R.drawable.ic_broken_image)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CaptureResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CaptureResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}