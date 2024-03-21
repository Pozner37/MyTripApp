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
    private var reviewsExecutor = Executors.newSingleThreadExecutor()
    private val firebaseModel = PostFirebaseModel()
    private val reviews: LiveData<MutableList<Post>>? = null
    val reviewsListLoadingState: MutableLiveData<LoadingState> =
        MutableLiveData(LoadingState.LOADED)


    companion object {
        val instance: PostModel = PostModel()
    }

    fun getAllPosts(): LiveData<MutableList<Post>> {
        refreshAllPosts()
        return reviews ?: database.postDao().getAll()
    }

    fun getMyPosts(): LiveData<MutableList<Post>> {
        refreshAllPosts()
        return reviews ?: database.postDao().getPostsByUserId(Firebase.auth.currentUser?.uid!!)
    }

    fun refreshAllPosts() {
        reviewsListLoadingState.value = LoadingState.LOADING

        val lastUpdated: Long = Post.lastUpdated

        firebaseModel.getAllPosts(lastUpdated) { list ->
            var time = lastUpdated
            for (post in list) {
                if (post.isDeleted) {
                    reviewsExecutor.execute {
                        database.postDao().delete(post)
                    }
                } else {
                    firebaseModel.getImage(post.id) { uri ->
                        reviewsExecutor.execute {
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
            reviewsListLoadingState.postValue(LoadingState.LOADED)
        }
    }

    fun addPost(review: Post, selectedImageUri: Uri, callback: () -> Unit) {
        firebaseModel.addPost(review) {
            firebaseModel.addPostImage(review.id, selectedImageUri) {
                refreshAllPosts()
                callback()
            }
        }
    }

    fun deletePost(review: Post?, callback: () -> Unit) {
        firebaseModel.deletePost(review) {
            refreshAllPosts()
            callback()
        }
    }

    fun updatePost(review: Post?, callback: () -> Unit) {
        firebaseModel.updatePost(review) {
            refreshAllPosts()
            callback()
        }
    }

    fun updatePostImage(reviewId: String, selectedImageUri: Uri, callback: () -> Unit) {
        firebaseModel.addPostImage(reviewId, selectedImageUri) {
            refreshAllPosts()
            callback()
        }
    }

    fun getPostImage(imageId: String, callback: (Uri) -> Unit) {
        firebaseModel.getImage(imageId, callback);
    }
}