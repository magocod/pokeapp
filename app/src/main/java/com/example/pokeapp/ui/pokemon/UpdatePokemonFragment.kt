package com.example.pokeapp.ui.pokemon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.pokeapp.R
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.PokemonRename
import com.example.pokeapp.ui.UserPokemonViewModel
import com.example.pokeapp.ui.UserPokemonViewModelFactory
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePokemonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatePokemonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val args: UpdatePokemonFragmentArgs by navArgs()

    private val loginViewModel: LoginViewModel by activityViewModels { LoginViewModelFactory() }
    private val userPokemonViewModel: UserPokemonViewModel by activityViewModels { UserPokemonViewModelFactory() }

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
        return inflater.inflate(R.layout.fragment_update_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        loginViewModel.login("u", "p")

        val rootLayout: LinearLayout = view.findViewById(R.id.rootLayout)
        val spinner: ProgressBar = view.findViewById(R.id.spinner)

        val nickNameEditText = view.findViewById<EditText>(R.id.input_nickname)
//        val argText = args.nickName
        nickNameEditText.setText(args.nickName)

        val confirmButton = view.findViewById<Button>(R.id.btn_confirm)
        confirmButton.isEnabled = false

        val releaseButton = view.findViewById<Button>(R.id.btn_release)

        // edit text text change listener
        nickNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int
            ) {
                if (p0.isNullOrBlank()) {
                    nickNameEditText.error = "nickname is required."
                    confirmButton.isEnabled = false
                } else {
                    nickNameEditText.error = null
//                    nameTextView.error = ""
                    confirmButton.isEnabled = true
                }
            }
        })

        confirmButton.setOnClickListener() {
            val tk = loginViewModel.getToken()
            if (tk != null) {
                userPokemonViewModel.pokemonRename(
                    args.UserPokemonId,
                    tk,
                    PokemonRename(
                        nickNameEditText.text.toString()
                    )
                )
                confirmButton.isEnabled = false
                releaseButton.isEnabled = false
            }
        }

        releaseButton.setOnClickListener() {
            val tk = loginViewModel.getToken()
            if (tk != null) {
                userPokemonViewModel.pokemonRelease(
                    args.UserPokemonId,
                    tk,
                )
                confirmButton.isEnabled = false
                releaseButton.isEnabled = false
            }
        }

        // rename
        userPokemonViewModel.capturedPokemon.observe(viewLifecycleOwner) { result ->
            result?.let {
                if (result is Result.Success) {
                    Log.d("update poke s name", result.toString())
                    showDialog("success", getString(R.string.temporary_message))
                } else {
                    Log.d("update poke e name", result.toString())
                    showDialog("error", result.toString())
                }
                if (nickNameEditText.text.toString().isNotEmpty()) {
                    confirmButton.isEnabled = true
                }
                releaseButton.isEnabled = true
                userPokemonViewModel.onCapturedShown()
            }
        }

        userPokemonViewModel.release.observe(viewLifecycleOwner) { result ->
            result?.let {
                if (result is Result.Success) {
                    Log.d("release poke s name", result.toString())
                    showDialog("success", getString(R.string.temporary_message))
                } else {
                    Log.d("release poke e name", result.toString())
                    showDialog("error", result.toString())
                }
                if (nickNameEditText.text.toString().isNotEmpty()) {
                    confirmButton.isEnabled = true
                }
                releaseButton.isEnabled = true
                userPokemonViewModel.onReleaseShown()
            }
        }

        // show the spinner when viewModel is true
        userPokemonViewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                if (show) {
                    spinner.visibility = View.VISIBLE
                } else {
                    spinner.visibility = View.GONE
                }
            }
        }

        // Show a snackbar whenever the viewModel is updated a non-null value
        userPokemonViewModel.snackbar.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                userPokemonViewModel.onSnackbarShown()
            }
        }

    }

    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
//            .setNegativeButton(R.string.cancel) { _, _ ->
//                // pass
//            }
            .setPositiveButton(R.string.ok) { _, _ ->
                // pass
            }
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdatePokemonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePokemonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}