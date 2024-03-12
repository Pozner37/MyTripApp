package com.mytrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mytrip.databinding.CountryPageBinding
import com.mytrip.posts.PostViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mytrip.R
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.mytrip.posts.PostsFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Marker
import viewModels.CountryFragmentViewModel


class CountryPageFragment : Fragment(), OnMapReadyCallback, PostsFragment.OnPostItemClickListener, GoogleMap.OnMarkerClickListener {

    private var _binding: CountryPageBinding? = null

    private val args: CountryPageFragmentArgs by navArgs()

    private val binding get() = _binding!!
    private val countryViewModel by activityViewModels<CountryFragmentViewModel>()
    private lateinit var viewModel: PostViewModel
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CountryPageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        countryViewModel.setCountry(args.country);
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)
        viewModel.countryPosts.observe(viewLifecycleOwner, Observer {
                posts -> posts.forEach{post ->
            val marker = map.addMarker(MarkerOptions().position(post.position))
            if (marker != null) {
                marker.tag = post.name
            }
        }
        })
        //   map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(34.0,32.0), 12f))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customPostsFragment.getFragment<PostsFragment>().setOnPostItemClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPostItemClicked(postId: String) {
        viewModel.countryPosts.observe(viewLifecycleOwner, Observer {
                posts ->
            val currPost = posts.find{curr -> curr.name === postId };
            if (currPost != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currPost.position, 9f))
            }
        })
    }

    override fun onMarkerClick(clickedMarker: Marker): Boolean {
        map.moveCamera( CameraUpdateFactory.newLatLngZoom(clickedMarker.position, 9f))
        binding.customPostsFragment.getFragment<PostsFragment>().onMarkerClicked(clickedMarker.tag.toString())
        return true;
    }
}