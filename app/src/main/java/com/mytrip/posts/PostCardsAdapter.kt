package com.mytrip.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mytrip.R
import com.mytrip.classes.Post
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip


class PostCardsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostCardsAdapter.PostViewHolder>() {
    private var onPostItemClickListener: OnPostItemClickListener? = null

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
        val country: Chip = itemView.findViewById(R.id.card_type)
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
    //    val x = getCountryFlag(post.countryName);
        Glide.with(holder.itemView)
            .load("https://cdn-v2.theculturetrip.com/1200x675/wp-content/uploads/2017/10/trolltunga--rob-bye-unsplash.webp")
            .into(holder.image)
        holder.image.contentDescription = post.description;
        holder.user.text = "User 1"
        holder.country.text = post.countryName
        holder.description.text = post.description
        handleClicksCard(holder, position);
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
        holder.country.setOnClickListener {
            onPostItemClickListener?.onPostCountryClicked(post.countryName)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}