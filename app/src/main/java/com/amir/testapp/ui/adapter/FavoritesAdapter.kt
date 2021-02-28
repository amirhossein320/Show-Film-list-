package com.amir.testapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amir.testapp.R
import com.amir.testapp.databinding.ContentItemBinding
import com.amir.testapp.data.model.Content
import com.amir.testapp.setImgFromUrl
import com.bumptech.glide.Glide

class FavoritesAdapter() : ListAdapter<Content, FavoritesAdapter.ViewHolder>(DiffCallback()) {

    private var context: Context? = null
    var onItemClick: ((Int) -> Unit)? = null
    var onFavoriteItemClick: ((Content) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.content_item,
                parent,
                false
            ) as ContentItemBinding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.contentItem.content = getItem(position)

        setImgFromUrl(holder.contentItem.imgThumb, getItem(position)?.thumbImage)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(getItem(position)?.id!!)
        }

        holder.contentItem.imgFavorite.setOnClickListener {
            onFavoriteItemClick?.invoke(getItem(position)!!)
        }

    }


    inner class ViewHolder(val contentItem: ContentItemBinding) :
        RecyclerView.ViewHolder(contentItem.root)

    class DiffCallback : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.equals(newItem)
        }

    }
}
