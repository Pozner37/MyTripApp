package com.example.mytrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mytrip.utils.getAllCountries

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)

        // Get the LinearLayout where you want to add buttons
        val buttonContainer = rootView.findViewById<LinearLayout>(R.id.buttonContainer)

        val countries = await {getAllCountries()  }

        // Create buttons dynamically and add them to the layout
        if (countries != null) {
            for (country in countries) {
                val imageButton = ImageButton(requireContext())
                imageButton.setImageResource(R.drawable.ic_launcher_background)
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

                // Add the button to the container
                buttonContainer.addView(imageButton)
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