package com.mytrip.modules.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mytrip.data.post.Post
import com.mytrip.data.user.User
import com.mytrip.data.user.UserModel


class PostViewModel : ViewModel() {
    var posts: LiveData<MutableList<Post>> = MutableLiveData();
    var users: LiveData<MutableList<User>> = MutableLiveData();

    fun assignPosts (postsList : LiveData<MutableList<Post>>) {
        posts = postsList;
        users = UserModel.instance.getAllUsers();
    }
}