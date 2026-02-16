package com.example.gallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class GalleryAdapter(val items : ArrayList<Image>, val context : Context, val clickListener: (Int) -> Unit) : RecyclerView.Adapter<GalleryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item : Image = items[position]
        holder.itemView.setOnClickListener { clickListener(position) }
        holder.imageView.setImageURI(Uri.parse(item.imagePath))
    }
}

class GalleryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById<ImageView>(R.id.imageView)
}