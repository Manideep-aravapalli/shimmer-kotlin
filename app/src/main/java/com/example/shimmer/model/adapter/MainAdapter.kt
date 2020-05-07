package com.example.shimmer.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shimmer.R
import com.example.shimmer.model.dto.Data
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.ArrayList

class MainAdapter(
    private var users: List<Data>, private var context: Context? = null
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = users.get(position).name
        holder.email.text = users.get(position).email
        Glide.with(holder.Imageurl.imageViewAvatar.context)
            .load(users.get(position).avatar)
            .into(holder.Imageurl.imageViewAvatar)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.textViewUserName
        val email = itemView.textViewUserEmail
        val Imageurl = itemView.imageViewAvatar
    }
}


