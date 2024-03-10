package com.example.mytrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mytrip.utils.CountriesApiManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)

        val buttonContainer = rootView.findViewById<GridLayout>(R.id.buttonContainer)

        val countriesApi = CountriesApiManager()

        lifecycleScope.launch {
            try {
                // Call the suspend function to get API response
                val countries =  countriesApi.getAllCountries()

                for (country in countries) {
                    val buttonLayout = LayoutInflater.from(requireContext()).inflate(R.layout.grid_item_button_with_title, null)
                    buttonLayout.layoutParams = GridLayout.LayoutParams().apply {
                width = 0 // Set width to 0 to allow GridLayout to control it
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Adjust weight as needed
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f) // Adjust weight as needed
            }
                    val imageButton: ImageButton = buttonLayout.findViewById(R.id.gridButton)
                    val imageButtonTitle: TextView = buttonLayout.findViewById(R.id.gridButtonTitle)

                    imageButton.setOnClickListener {
                        Toast.makeText(context, country.name.common, Toast.LENGTH_SHORT).show()
                    }

                    Picasso.get().load(country.flags.png).into(imageButton)

                    imageButtonTitle.text = country.name.common

                   (buttonLayout.parent as? ViewGroup)?.removeView(buttonLayout)

                    // Add the button to the container
                    buttonContainer.addView(buttonLayout)
                }
            } catch (e: Exception) {
                Log.e("HomePage", "Error while creating home page: ${e.message}")
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}