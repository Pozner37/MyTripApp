package com.mytrip.modules.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mytrip.BasePostMapFragment
import com.mytrip.CountryPageFragmentDirections
import com.mytrip.R
import com.mytrip.classes.Post
import com.mytrip.viewModels.UserViewModel

class PostsFragment : Fragment(), PostCardsAdapter.OnPostItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private val viewModel by activityViewModels<PostViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    private var onPostItemClickListener: OnPostItemClickListener? = null

    interface OnPostItemClickListener {
        fun onPostItemClicked(postId: String)
    }

    fun observePostViewModel(
        recyclerView: RecyclerView,
        posts: LiveData<MutableList<Post>>?
    ) {
        posts?.observe(viewLifecycleOwner) { currPosts: List<Post> ->
            val postCardsAdapter = PostCardsAdapter(currPosts,userViewModel)
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

    override fun onPostDeleteClicked(postId: String) {
        viewModel.posts.value?.let { posts ->
            viewModel.setPosts(posts.filterNot { it.id == postId }.toMutableList())
        }
    }

    override fun onPostEditClicked(post: Post) {
        try {
            val action = CountryPageFragmentDirections.actionCountryPageFragmentToCreatePostFragment(post);
            findNavController().navigate(action)
        } catch (e: Exception) {
            e.message?.let { Log.d("PostCreate", it) }
        }

    }

    override fun onPostCountryClicked(countryName: String) {
        val navController = findNavController()
        if (navController.currentDestination?.id != R.id.CountryPageFragment) {
            val action = PostsFragmentDirections.postCountryToCountryPageFragment(countryName);
            findNavController().navigate(action)
        }
    }
    fun onMarkerClicked(postId: String) {
        val index = viewModel.posts.value?.indexOfFirst { post -> post.id == postId }
        if (index != null) {
            recyclerView.scrollToPosition(index)
        }
    }
}