package com.example.mytrip.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.util.toHalf
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrip.R
import com.example.mytrip.classes.Post


class PostCardsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostCardsAdapter.PostViewHolder>() {
    private var onPostItemClickListener: OnPostItemClickListener? = null

    interface OnPostItemClickListener {
        fun onPostItemClicked(
            postId : String
        )
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.card_image)
        val title: TextView = itemView.findViewById(R.id.card_title)
        val type: TextView = itemView.findViewById(R.id.card_type)
        val description: TextView = itemView.findViewById(R.id.card_description)
        val rating: TextView = itemView.findViewById(R.id.card_rating)
        val card: CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_card, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.title.text = post.name
        holder.type.text = post.name.toString()
        holder.description.text = post.name
        holder.rating.text = "Lat: ${post.position.latitude.toHalf()}, Lon: ${post.position.longitude.toHalf()}"
        handleClicksCard(holder, position);
    }

    fun setOnPostItemClickListener(listener: OnPostItemClickListener) {
        onPostItemClickListener = listener
    }

    private fun handleClicksCard(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.card.setOnClickListener {
            onPostItemClickListener?.onPostItemClicked(post.name)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}