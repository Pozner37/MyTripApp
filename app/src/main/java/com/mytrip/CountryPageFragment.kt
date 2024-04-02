package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.mytrip.classes.Country
import com.mytrip.data.post.PostModel
import com.mytrip.modules.posts.PostViewModel
import com.mytrip.utils.CountriesApiManager
import kotlinx.coroutines.launch


class CountryPageFragment : BasePostMapFragment() {

    private val args: CountryPageFragmentArgs by navArgs()
    private val viewModel by activityViewModels<PostViewModel>()
    private lateinit var country : Country;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)

        viewModel.assignPosts(PostModel.instance.getCountryPosts(args.countryCode));

        lifecycleScope.launch {
            fetchCountry()
        }

        return view;
    }

    private suspend fun fetchCountry() {
        country = CountriesApiManager().getCountryByCode(args.countryCode)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = country.name.common;
        super.map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(country.countryPosition[0], country.countryPosition[1]), 6f))
    }

}