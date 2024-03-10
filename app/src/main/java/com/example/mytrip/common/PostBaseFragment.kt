package com.example.mytrip.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrip.models.Post
import com.example.mytrip.posts.PostCardsAdapter

abstract class PostBaseFragment : Fragment(), PostCardsAdapter.OnPostItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResourceId(), container, false)
        return view
    }

    abstract fun getLayoutResourceId(): Int

    fun observePostViewModel(
        recyclerView: RecyclerView,
        posts: LiveData<MutableList<Post>>?
    ) {
        posts?.observe(viewLifecycleOwner) { currReviews: List<Post> ->
            val reviewCardsAdapter = PostCardsAdapter(currReviews)
            reviewCardsAdapter.setOnPostItemClickListener(this)
            recyclerView.adapter = reviewCardsAdapter
            //closeKeyboard(requireContext(), requireView())
        }
    }

//    override fun onPostItemClicked(
//        reviewId: String, reviewEmail: String,
//        holder: PostCardsAdapter.ReviewViewHolder, mode: String
//    ) {
//        if (mode == "DeleteCard") {
//            MaterialAlertDialogBuilder(requireContext())
//                .setTitle("Delete Review")
//                .setMessage("Are you sure you want to delete this review?")
//                .setPositiveButton("Delete") { _, _ ->
//                  //  deleteCardHandler(reviewId)
//                }
//                .setNegativeButton("Cancel", null)
//                .show()
//        }
//    }
}