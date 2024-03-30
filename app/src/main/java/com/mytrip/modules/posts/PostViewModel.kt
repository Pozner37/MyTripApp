package com.mytrip.modules.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytrip.data.post.Post


class PostViewModel : ViewModel() {
    var posts: MutableLiveData<MutableList<Post>> = MutableLiveData();

    fun setPosts(itemsArray: MutableList<Post>) {
        posts.value = itemsArray
    }
}