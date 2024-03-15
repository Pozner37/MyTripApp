package com.mytrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mytrip.utils.CountriesApiManager
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
                generateCountryButtons(countriesApi, buttonContainer)
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

    private suspend fun generateCountryButtons(countriesApi: CountriesApiManager, buttonContainer: GridLayout) {
        val countries =  countriesApi.getAllCountries()

        stopSpinnerLoading(buttonContainer)

        for (country in countries) {
            val buttonLayout = LayoutInflater.from(requireContext()).inflate(R.layout.grid_item_button_with_title, null)
            buttonLayout.layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1,1f)
            }
            val imageButton: ImageButton = buttonLayout.findViewById(R.id.gridButton)
            val imageButtonTitle: TextView = buttonLayout.findViewById(R.id.gridButtonTitle)

            imageButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCountryFragment(country)
                findNavController().navigate(action)
            }

            Picasso.get().load(country.flags.png).into(imageButton)

            imageButtonTitle.text = country.name.common

            (buttonLayout.parent as? ViewGroup)?.removeView(buttonLayout)

            buttonContainer.addView(buttonLayout)
        }
    }

    private fun stopSpinnerLoading(buttonContainer: GridLayout) {
        val spinner = buttonContainer.findViewById<ProgressBar>(R.id.progressBar);
        buttonContainer.removeView(spinner)
    }
}