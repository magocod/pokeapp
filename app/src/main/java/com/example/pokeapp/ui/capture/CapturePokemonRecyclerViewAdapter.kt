package com.example.pokeapp.ui.capture

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.ui.capture.dummy.DummyContent.DummyItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CapturePokemonRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<CapturePokemonRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_capture_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        holder.itemView.setOnClickListener() {
            Log.d("CapturePRecycler", "position: $position id: ${item.id}")
            showInputDialog(holder.itemView.context, item.content)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    private fun showDialog(context: Context) {
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

    private fun showInputDialog(context: Context, pokemonName: String = "") {
        val builder = MaterialAlertDialogBuilder(context)

        // dialog title
        builder.setTitle("name the pokemon")

        // dialog message view
        val constraintLayout = getEditTextLayout(context)
        builder.setView(constraintLayout)

        val textInputLayout =
            constraintLayout.findViewWithTag<TextInputLayout>("textInputLayoutTag")
        val textInputEditText =
            constraintLayout.findViewWithTag<TextInputEditText>("textInputEditTextTag")

        textInputEditText.setText(pokemonName)

        // alert dialog positive button
        builder.setPositiveButton("Capture") { dialog, which ->
            val name = textInputEditText.text
        }

        // alert dialog other buttons
//        builder.setNegativeButton("No",null)
        builder.setNeutralButton("Cancel", null)

        // set dialog non cancelable
        builder.setCancelable(false)

        // finally, create the alert dialog and show it
        val dialog = builder.create()

        dialog.show()

        // initially disable the positive button
        if (pokemonName.isEmpty()) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        // edit text text change listener
        textInputEditText.addTextChangedListener(object : TextWatcher {
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
                    textInputLayout.error = "Name is required."
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .isEnabled = false
                } else {
                    textInputLayout.error = ""
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .isEnabled = true
                }
            }
        })
    }
}

// get edit text layout
fun getEditTextLayout(context: Context): ConstraintLayout {
    val constraintLayout = ConstraintLayout(context)
    val layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    constraintLayout.layoutParams = layoutParams
    constraintLayout.id = View.generateViewId()

    val textInputLayout = TextInputLayout(context)
    textInputLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
    layoutParams.setMargins(
        32.toDp(context),
        8.toDp(context),
        32.toDp(context),
        8.toDp(context)
    )
    textInputLayout.layoutParams = layoutParams
    textInputLayout.hint = "Name"
    textInputLayout.id = View.generateViewId()
    textInputLayout.tag = "textInputLayoutTag"


    val textInputEditText = TextInputEditText(context)
    textInputEditText.id = View.generateViewId()
    textInputEditText.tag = "textInputEditTextTag"

    textInputLayout.addView(textInputEditText)

    val constraintSet = ConstraintSet()
    constraintSet.clone(constraintLayout)

    constraintLayout.addView(textInputLayout)
    return constraintLayout
}


// extension method to convert pixels to dp
fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()
