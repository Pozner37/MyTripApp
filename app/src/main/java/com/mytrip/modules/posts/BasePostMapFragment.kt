package com.mytrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.mytrip.databinding.PostsWithMapBinding
import com.mytrip.data.post.Post
import com.mytrip.data.post.SerializableLatLng
import com.mytrip.modules.posts.PostViewModel
import com.mytrip.modules.posts.PostsFragment
import com.mytrip.viewModels.LocationViewModel


abstract class BasePostMapFragment : Fragment(), OnMapReadyCallback, PostsFragment.OnPostItemClickListener, GoogleMap.OnMarkerClickListener {

    private var _binding: PostsWithMapBinding? = null

    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val viewModel by activityViewModels<PostViewModel>()
    private val locationViewModel by activityViewModels<LocationViewModel>()
    private var currLocationMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PostsWithMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)
        map.setOnMapLongClickListener {
            val tempPost = Post("", "", "", "", SerializableLatLng.fromGoogleLatLng(it))
            val action = CountryPageFragmentDirections.toCreatePostFragment(tempPost)
            findNavController().navigate(action)
        }
        locationViewModel.location.observe(viewLifecycleOwner, Observer {
            currLocationMarker?.remove()
            currLocationMarker = map.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))!!
        })
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            posts ->
            map.clear()
            currLocationMarker = map.addMarker(MarkerOptions().position(LatLng(locationViewModel.location.value?.latitude!!, locationViewModel.location.value!!.longitude)))!!
            posts.forEach{post ->
            val marker = map.addMarker(MarkerOptions().position(LatLng(post.position.latitude, post.position.longitude)))
            if (marker != null) {
                marker.tag = post.id
            }
        }
        })
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
        val post = viewModel.posts.value?.find { curr -> curr.id === postId }
        if (post != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(post.position.toGoogleLatLng(), 9f))
        }
    }

    override fun onMarkerClick(clickedMarker: Marker): Boolean {
        map.moveCamera( CameraUpdateFactory.newLatLngZoom(clickedMarker.position, 9f))
        binding.customPostsFragment.getFragment<PostsFragment>().onMarkerClicked(clickedMarker.tag.toString())
        return true;
    }
}