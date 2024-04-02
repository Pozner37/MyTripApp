package com.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mytrip.data.post.PostModel
import com.mytrip.modules.posts.PostViewModel


class MyPostsFragment : BasePostMapFragment() {

    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.assignPosts(PostModel.instance.getMyPosts());
        view?.findViewById<FloatingActionButton>(R.id.fab)?.isVisible = false;
        return view;
    }
}