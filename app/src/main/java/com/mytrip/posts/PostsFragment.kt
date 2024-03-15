package com.mytrip.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mytrip.BasePostMapFragment
import com.mytrip.R
import com.mytrip.classes.Post

class PostsFragment : Fragment(), PostCardsAdapter.OnPostItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private val viewModel by activityViewModels<PostViewModel>()
    private var onPostItemClickListener: OnPostItemClickListener? = null

    interface OnPostItemClickListener {
        fun onPostItemClicked(postId: String)
    }

    fun observePostViewModel(
        recyclerView: RecyclerView,
        posts: LiveData<MutableList<Post>>?
    ) {
        posts?.observe(viewLifecycleOwner) { currPosts: List<Post> ->
            val postCardsAdapter = PostCardsAdapter(currPosts)
            postCardsAdapter.setOnPostItemClickListener(this)
            recyclerView.adapter = postCardsAdapter
            //closeKeyboard(requireContext(), requireView())
        }
    }

    fun getLayoutResourceId(): Int {
        return R.layout.posts_list_fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(getLayoutResourceId(), container, false)
        if (view != null) {
            initViews(view)
        }

        setupRecyclerView()
        observePostViewModel()
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.posts_recycler_view)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observePostViewModel() {
        observePostViewModel(recyclerView, viewModel.posts)
    }

    fun setOnPostItemClickListener(listener: BasePostMapFragment) {
        onPostItemClickListener = listener
    }

    // Call the listener when an item is clicked
    override fun onPostItemClicked(postId: String) {
        onPostItemClickListener?.onPostItemClicked(postId)
    }

    fun onMarkerClicked(postId: String) {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
                posts -> val index  = posts.indexOfFirst { post -> post.id === postId }
            recyclerView.scrollToPosition(index)
        })
    }
}