package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mytrip.data.post.Post
import com.mytrip.data.post.PostModel
import com.mytrip.modules.posts.PostViewModel
import com.mytrip.viewModels.UserViewModel


class MyProfileFragment : BasePostMapFragment() {

    private val viewModel by activityViewModels<PostViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.posts = PostModel.instance.getMyPosts();
        view?.findViewById<FloatingActionButton>(R.id.fab)?.isVisible = false;
        return view;
    }
}