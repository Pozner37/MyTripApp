package com.mytrip.modules.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytrip.data.post.Post


class PostViewModel : ViewModel() {
    var posts: LiveData<MutableList<Post>> = MutableLiveData();
}