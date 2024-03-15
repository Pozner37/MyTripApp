package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.mytrip.posts.PostViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mytrip.classes.Post


class MyProfileFragment : BasePostMapFragment() {

    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.setPosts(mutableListOf(
            Post("1","Israel","My Post 1","picture", LatLng(32.0,35.0)),
            Post("2","Israel","My Post 2","picture", LatLng(33.0,34.0)),
            Post("3","Israel","My Post 3","picture", LatLng(31.0,35.0)),
            Post("4","Israel","My Post 4","picture", LatLng(31.0,35.5))
        ))
        view?.findViewById<FloatingActionButton>(R.id.fab)?.isVisible = false;
        return view;
    }
}