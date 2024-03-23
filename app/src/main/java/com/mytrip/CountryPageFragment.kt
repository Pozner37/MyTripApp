package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
    private  lateinit var countryName : String;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        countryName = args.countryName;
        viewModel.setPosts(mutableListOf(
            Post("1",countryName,"Post 1", LatLng(32.0,35.0)),
            Post("2",countryName,"Post 2", LatLng(33.0,34.0)),
            Post("3",countryName,"Post 3", LatLng(31.0,35.0)),
            Post("4",countryName,"Post 4", LatLng(31.0,35.5))
        ))

        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { view ->
            val action = CountryPageFragmentDirections.actionCountryPageFragmentToCreatePostFragment(args.countryName)
            findNavController().navigate(action)
        }
        return view;
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = countryName
    }
}