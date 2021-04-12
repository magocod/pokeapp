package com.example.pokeapp.ui.team

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokeapp.R
import com.example.pokeapp.data.Result
import com.example.pokeapp.network.Move
import com.example.pokeapp.network.SwapPartyMember
import com.example.pokeapp.network.UserPokemon
import com.example.pokeapp.ui.UserPokemonViewModel
import com.example.pokeapp.ui.UserPokemonViewModelFactory
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SwapPokemonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SwapPokemonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swap_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var enteringTheParty: Int? = null
        var leavingTheParty: Int? = null

        // load data
        val token = loginViewModel.getToken()
        if (token != null) {
            userPokemonViewModel.getPokemonParty(token)
            userPokemonViewModel.getPokemonStorage(token)
        }

        val autoTextViewStorage = view.findViewById<AutoCompleteTextView>(R.id.autoTextViewStorage)
        val autoTextViewTeam = view.findViewById<AutoCompleteTextView>(R.id.autoTextViewTeam)
        val confirmButton = view.findViewById<Button>(R.id.btn_confirm)
        confirmButton.isEnabled = false

        // Get the array of elements
//        val values = listOf<String>()
//        updateAutoCompleteTextView(autoTextViewStorage, values)
//        updateAutoCompleteTextView(autoTextViewTeam, values)
        val values = listOf<UserPokemon>()
        updateSwapAutoComplete(autoTextViewStorage, values)
        updateSwapAutoComplete(autoTextViewTeam, values)

        confirmButton.setOnClickListener() {
//            val storageValue = autoTextViewStorage.text.toString()
//            Log.d("AutoComplete", storageValue)
            Log.d("AutoComplete enter", enteringTheParty.toString())
            Log.d("AutoComplete leave", leavingTheParty.toString())

            val tk = loginViewModel.getToken()
            if (tk != null) {
                val reqData = SwapPartyMember(
                    enteringTheParty,
                    leavingTheParty
                )
                Log.d("AutoComplete string", reqData.toString())
//                val moshi = Moshi.Builder()
//                    .add(KotlinJsonAdapterFactory())
//                    .build()
//                val jsonAdapter = moshi.adapter(SwapPartyMember::class.java)
//                Log.d("AutoComplete serial", jsonAdapter.serializeNulls().toJson(reqData))
//                val gson = GsonBuilder().serializeNulls().create()
//                Log.d("AutoComplete serial", gson.toJson(reqData)
                userPokemonViewModel.swapPartyMember(
                    tk,
                    reqData
                )
            }

        }

        // edit text text change listener
        autoTextViewStorage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrBlank()) {
                    autoTextViewStorage.error = "${getString(R.string.enter_the_team)} is required."
                    if (autoTextViewTeam.text.toString().isEmpty()) {
                        confirmButton.isEnabled = false
                    }
//                    confirmButton.isEnabled = false
                } else {
                    autoTextViewStorage.error = null
//                    if (autoTextViewTeam.text.toString().isNotEmpty()) {
//                        confirmButton.isEnabled = true
//                    }
                    confirmButton.isEnabled = true
                }
            }

            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int,
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int,
            ) {
            }
        })

        // edit text text change listener
        autoTextViewTeam.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrBlank()) {
                    autoTextViewTeam.error = "${getString(R.string.leaves_the_team)} is required."
                    if (autoTextViewStorage.text.toString().isEmpty()) {
                        confirmButton.isEnabled = false
                    }
//                    confirmButton.isEnabled = false
                } else {
                    autoTextViewTeam.error = null
//                    if (autoTextViewStorage.text.toString().isNotEmpty()) {
//                        confirmButton.isEnabled = true
//                    }
                    confirmButton.isEnabled = true
                }
            }

            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int,
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?, p1: Int,
                p2: Int, p3: Int,
            ) {
            }
        })

        autoTextViewStorage.setOnItemClickListener { adapterView, _, i, l ->
            val move = adapterView.getItemAtPosition(i)
            // for adapter
//            if (move is Move) {
//                Log.d("AutoComplete","Storage selected item: ${move.id} ${move.name}")
//            }
            if (move is UserPokemon) {
                Log.d("AutoComplete", "Storage selected item: ${move.id} ${move.nickName}")
                enteringTheParty = move.id
            }
        }

        autoTextViewTeam.setOnItemClickListener { adapterView, _, i, l ->
            val move = adapterView.getItemAtPosition(i)
            // for adapter
//            if (move is Move) {
//                Log.d("AutoComplete","Team selected item: ${move.id} ${move.name}")
//            }
            if (move is UserPokemon) {
                Log.d("AutoComplete", "Team selected item: ${move.id} ${move.nickName}")
                leavingTheParty = move.id
            }
        }

        loginViewModel.login("u", "p")

        userPokemonViewModel.team.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { team ->
//                val newObjects = team.map { userPokemon ->
//                    "${userPokemon.id} ${userPokemon.nickName} ${userPokemon.specie.name}"
//                }
//                Log.d("auto", newObjects.toString())
//                updateAutoCompleteTextView(autoTextViewTeam, newObjects)
                updateSwapAutoComplete(autoTextViewTeam, team)
            })

        userPokemonViewModel.storage.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { storage ->
//                val newObjects = storage.map { userPokemon ->
//                    "${userPokemon.id} ${userPokemon.nickName} ${userPokemon.specie.name}"
//                }
//                updateAutoCompleteTextView(autoTextViewStorage, newObjects)
                updateSwapAutoComplete(autoTextViewStorage, storage)
            })

        userPokemonViewModel.swap.observe(viewLifecycleOwner) { result ->
            result?.let {
                if (result is Result.Success) {
                    Log.d("swap poke s", result.toString())
                    showDialog("success", getString(R.string.temporary_message))
                } else {
                    Log.d("swap poke e", result.toString())
                    showDialog("error", result.toString())
                }
                userPokemonViewModel.onSwapShown()
            }
        }

        val spinner: ProgressBar = view.findViewById(R.id.spinner)

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
    }

    private fun updateAutoCompleteTextView(
        autoCompleteTextView: AutoCompleteTextView,
        objects: List<String>,
    ) {
        // Create adapter and add in AutoCompleteTextView
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            objects
//        )
        val adapter = AutoCompleteTextViewAdapter(
            requireContext(),
            R.layout.autocomplete_custom_row_2,
            arrayOf(
                Move(1, "poison"),
                Move(2, "water"),
                Move(3, "y"),
                Move(4, "ye")
            )
        )
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun updateSwapAutoComplete(
        autoCompleteTextView: AutoCompleteTextView,
        objects: List<UserPokemon>,
    ) {
        // Create adapter and add in AutoCompleteTextView
        val adapter = SwapPokemonAdapter(
            requireContext(),
            R.layout.autocomplete_custom_row_2,
            objects.toTypedArray()
        )
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
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
         * @return A new instance of fragment SwapPokemonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SwapPokemonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}