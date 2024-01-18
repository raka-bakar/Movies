package com.raka.movies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.databinding.ItemFavoriteBinding
import com.raka.movies.databinding.ItemFavoriteLastBinding

class HomeFavoriteAdapter(
    private val listData: List<MovieItemCompact>,
    private val onItemClick: (MovieItemCompact) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewBinding
        if (viewType == TYPE_ITEM) {
            binding =
                ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeFavouriteHolder(binding)
        } else if (viewType == TYPE_FOOTER) {
            binding =
                ItemFavoriteLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeFavoriteLastHolder(binding)
        }
        throw RuntimeException("Unknown view type!")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ITEM -> {
                (holder as HomeFavouriteHolder).bind(listData[position])
                holder.itemView.setOnClickListener { onItemClick(listData[position]) }
            }

            TYPE_FOOTER -> {}
        }
    }

    override fun getItemCount(): Int {
        return if (listData.isEmpty()) {
            0
        } else {
            listData.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listData.size) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    inner class HomeFavouriteHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItemCompact: MovieItemCompact) {
            binding.ivFavorite.load(movieItemCompact.posterUrl)
        }
    }

    inner class HomeFavoriteLastHolder(binding: ItemFavoriteLastBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 0
    }
}