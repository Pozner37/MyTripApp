package com.mytrip.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytrip.classes.Post
import com.google.android.gms.maps.model.LatLng


class PostViewModel : ViewModel() {
    private val _countryPosts = MutableLiveData<MutableList<Post>>()
    private val _myPosts = MutableLiveData<MutableList<Post>>() // Added for myPosts

    val countryPosts: LiveData<MutableList<Post>> = _countryPosts
    val myPosts: LiveData<MutableList<Post>> = _myPosts // Exposing LiveData for myPosts

    init {
        // Initialize with dummy data (replace with your actual data source)
        val dummyPosts = mutableListOf(
            Post("Post 1", LatLng(32.0, 35.0)),
            Post("Post 2", LatLng(32.3, 34.1)),
            Post("Post 3", LatLng(32.5, 34.5))
        )
        _countryPosts.value = dummyPosts;

        // Initialize myPosts with dummy data (replace with your actual data source)
        val dummyMyPosts = mutableListOf(
            Post("My Post 1", LatLng(37.7128, -74.0060)),
            Post("My Post 2", LatLng(37.7128, -74.0060))
        )
        _myPosts.value = dummyMyPosts
    }

    // Function to update the posts MutableList (if needed)
    fun updatePosts(posts: MutableList<Post>) {
        _countryPosts.value = posts
    }

    // Function to update the myPosts MutableList (if needed)
    fun updateMyPosts(myPosts: MutableList<Post>) {
        _myPosts.value = myPosts
    }
}