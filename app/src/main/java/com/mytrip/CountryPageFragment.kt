package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mytrip.posts.PostViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mytrip.classes.Post


class CountryPageFragment : BasePostMapFragment() {

    private val args: CountryPageFragmentArgs by navArgs()
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.setPosts(mutableListOf(
            Post("1",args.country.name.common,"Post 1", LatLng(32.0,35.0)),
            Post("2",args.country.name.common,"Post 2", LatLng(33.0,34.0)),
            Post("3",args.country.name.common,"Post 3", LatLng(31.0,35.0)),
            Post("4",args.country.name.common,"Post 4", LatLng(31.0,35.5))
        ))

        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { view ->
            val action = CountryPageFragmentDirections.actionCountryPageFragmentToCreatePostFragment(args.country)
            findNavController().navigate(action)
        }
        return view;
    }
}