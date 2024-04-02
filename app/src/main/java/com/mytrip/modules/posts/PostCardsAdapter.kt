package com.mytrip.modules.posts

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mytrip.R
import com.mytrip.data.post.Post
import com.mytrip.data.user.User
import com.mytrip.data.user.UserModel
import com.mytrip.utils.CountriesApiManager
import com.mytrip.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostCardsAdapter(private val posts: List<Post>, private val users: List<User>) :
    RecyclerView.Adapter<PostCardsAdapter.PostViewHolder>() {
    private var onPostItemClickListener: OnPostItemClickListener? = null;
    private val userId = Firebase.auth.uid;
    interface OnPostItemClickListener {
        fun onPostItemClicked(
            postId : String
        )
        fun onPostDeleteClicked(
            postId : String
        )
        fun onPostEditClicked(
            post : Post
        )
        fun onPostCountryClicked(
            countryName : String
        )
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.card_image)
        val user: TextView = itemView.findViewById(R.id.card_user)
        val countryImage: ImageButton = itemView.findViewById(R.id.country_image)
        val description: TextView = itemView.findViewById(R.id.card_description)
        val card: CardView = itemView.findViewById(R.id.card)
        val deleteBtn : ImageButton = itemView.findViewById(R.id.delete_button)
        val editBtn : ImageButton = itemView.findViewById(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        val user = users.find { it.id == post.userId }
        CoroutineScope(Dispatchers.Main).launch {
            val x = getCountryFlag(post.country);
            Glide.with(holder.itemView)
                .asBitmap()
                .load(x)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        holder.countryImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // This method is intentionally empty
                    }
                })
        }
        Glide.with(holder.itemView)
            .load(post.photo)
            .into(holder.image)
        holder.image.contentDescription = post.description
        holder.user.text = "${user?.firstName} ${user?.lastName}"
        holder.description.text = post.description

        holder.deleteBtn.isVisible = post.userId == userId
        holder.editBtn.isVisible = post.userId == userId

        handleClicksCard(holder, position);
    }

    private suspend fun getCountryFlag(countryCode : String) : String {
        return withContext(Dispatchers.IO) {
            CountriesApiManager().getCountryFlag(countryCode).execute().body()?.flags?.png
                ?: ""
        }
    }

    fun setOnPostItemClickListener(listener: OnPostItemClickListener) {
        onPostItemClickListener = listener
    }

    private fun handleClicksCard(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.card.setOnClickListener {
            onPostItemClickListener?.onPostItemClicked(post.id)
        }
        holder.deleteBtn.setOnClickListener {
            onPostItemClickListener?.onPostDeleteClicked(post.id)
        }
        holder.editBtn.setOnClickListener {
            onPostItemClickListener?.onPostEditClicked(post)
        }
        holder.countryImage.setOnClickListener {
            onPostItemClickListener?.onPostCountryClicked(post.country)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}