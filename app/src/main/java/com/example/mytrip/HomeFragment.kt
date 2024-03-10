package com.example.mytrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
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

        // Get the LinearLayout where you want to add buttons
        val buttonContainer = rootView.findViewById<GridLayout>(R.id.buttonContainer)

        val countriesApi = CountriesApiManager()

        lifecycleScope.launch {
            try {
                // Call the suspend function to get API response
                val countries =  countriesApi.getAllCountries()

                for (country in countries) {
                    val imageButton = ImageButton(requireContext())
//                    imageButton.setImageResource(R.drawable.ic_launcher_background)
                    val sizeInDp = 100
                    val density = resources.displayMetrics.density
                    val sizeInPixels = (sizeInDp * density).toInt()

                    val layoutParams = ViewGroup.LayoutParams(sizeInPixels, sizeInPixels)
                    imageButton.background = null
                    imageButton.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    imageButton.layoutParams = layoutParams
                    imageButton.setOnClickListener {
                        Toast.makeText(context, country.name.common, Toast.LENGTH_SHORT).show()
                    }

                    val imageUrl = country.flags.png

                    Picasso.get().load(imageUrl).into(imageButton)

                    // Add the button to the container
                    buttonContainer.addView(imageButton)
                }
            } catch (e: Exception) {
                // Handle errors
                Log.e("YourFragment", "Error fetching data: ${e.message}")
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