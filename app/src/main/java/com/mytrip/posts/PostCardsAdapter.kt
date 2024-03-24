package com.mytrip.posts

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mytrip.R
import com.mytrip.classes.Post
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.mytrip.classes.Flag
import com.mytrip.utils.CountriesApiManager
import com.mytrip.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PostCardsAdapter(private val posts: List<Post>, private val userViewModel: UserViewModel) :
    RecyclerView.Adapter<PostCardsAdapter.PostViewHolder>() {
    private var onPostItemClickListener: OnPostItemClickListener? = null;


    interface OnPostItemClickListener {
        fun onPostItemClicked(
            postId : String
        )
        fun onPostDeleteClicked(
            postId : String
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        CoroutineScope(Dispatchers.Main).launch {
            val x = getCountryFlag(post.countryName);
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
            .load("https://cdn-v2.theculturetrip.com/1200x675/wp-content/uploads/2017/10/trolltunga--rob-bye-unsplash.webp")
            .into(holder.image)
        holder.image.contentDescription = post.description
        holder.user.text = "User ${post.userId}"
        holder.description.text = post.description

        holder.deleteBtn.isVisible = post.userId == userViewModel.user.id

        handleClicksCard(holder, position);
    }

    private suspend fun getCountryFlag(countryName : String) : String {
        return withContext(Dispatchers.IO) {
            CountriesApiManager().getCountryFlag(countryName).execute().body()?.get(0)?.flags?.png
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
        holder.countryImage.setOnClickListener {
            onPostItemClickListener?.onPostCountryClicked(post.countryName)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}