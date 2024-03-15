package com.mytrip.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytrip.classes.Post
import com.google.android.gms.maps.model.LatLng


class PostViewModel : ViewModel() {
    var posts: MutableLiveData<MutableList<Post>> = MutableLiveData();

    fun setPosts(itemsArray: MutableList<Post>) {
        posts.value = itemsArray
    }
}