package com.mytrip.data.post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mytrip.data.AppLocalDatabase

class PostModel private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDatabase.db
    private var postsExecutor = Executors.newSingleThreadExecutor()
    private val firebaseModel = PostFirebaseModel()
    private val posts: LiveData<MutableList<Post>>? = null
    val postsListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.LOADED)


    companion object {
        val instance: PostModel = PostModel()
    }

    fun getAllPosts(): LiveData<MutableList<Post>> {
        refreshAllPosts()
        return posts ?: database.postDao().getAll()
    }

    fun getMyPosts(): LiveData<MutableList<Post>> {
        refreshAllPosts()
        return posts ?: database.postDao().getPostsByUserId(Firebase.auth.currentUser?.uid!!)
    }

    fun refreshAllPosts() {
        postsListLoadingState.value = LoadingState.LOADING

        val lastUpdated: Long = Post.lastUpdated

        firebaseModel.getAllPosts(lastUpdated) { list ->
            var time = lastUpdated
            for (post in list) {
                if (post.isDeleted) {
                    postsExecutor.execute {
                        database.postDao().delete(post)
                    }
                } else {
                    firebaseModel.getImage(post.id) { uri ->
                        postsExecutor.execute {
                            post.photo = uri.toString()
                            database.postDao().insert(post)
                        }
                    }

                    post.timestamp?.let {
                        if (time < it)
                            time = post.timestamp ?: System.currentTimeMillis()
                    }
                    Post.lastUpdated = time
                }
            }
            postsListLoadingState.postValue(LoadingState.LOADED)
        }
    }

    fun addPost(post: Post, selectedImageUri: Uri, callback: () -> Unit) {
        firebaseModel.addPost(post) {
            firebaseModel.addPostImage(post.id, selectedImageUri) {
                refreshAllPosts()
                callback()
            }
        }
    }

    fun deletePost(post: Post?, callback: () -> Unit) {
        firebaseModel.deletePost(post) {
            refreshAllPosts()
            callback()
        }
    }

    fun updatePost(post: Post?, callback: () -> Unit) {
        firebaseModel.updatePost(post) {
            refreshAllPosts()
            callback()
        }
    }

    fun updatePostImage(postId: String, selectedImageUri: Uri, callback: () -> Unit) {
        firebaseModel.addPostImage(postId, selectedImageUri) {
            refreshAllPosts()
            callback()
        }
    }

    fun getPostImage(imageId: String, callback: (Uri) -> Unit) {
        firebaseModel.getImage(imageId, callback);
    }
}