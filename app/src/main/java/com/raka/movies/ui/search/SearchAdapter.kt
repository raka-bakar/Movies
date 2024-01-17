package com.raka.movies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.raka.movies.R
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.databinding.ItemMovieBinding

class SearchAdapter(
    private val originalList: List<MovieItemCompact>,
    private val onFavoriteClick: (MovieItemCompact) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    private var listData: MutableList<MovieItemCompact> =
        originalList as MutableList<MovieItemCompact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val v = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    private fun onClickItem(item: MovieItemCompact, position: Int) {
        onFavoriteClick(item)
        notifyItemChanged(position)
    }

    fun setFilter(text: String) {
        val filteredList: MutableList<MovieItemCompact> = mutableListOf()
        listData = if (text?.isNotEmpty() == true) {
            for (item in originalList) {
                if (text.lowercase().let { item.title.lowercase().contains(it) }) {
                    filteredList.add(item)
                }
            }
            filteredList
        } else {
            originalList.toMutableList()
        }
        notifyDataSetChanged()
    }

    inner class SearchHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: MovieItemCompact,
            position: Int
        ) {
            binding.ivPoster.load(item.posterUrl) {
                transformations(RoundedCornersTransformation(RADIUS_CORNER))
            }
            val year = item.releaseDate?.substring(0, 4)
            binding.tvYear.text = year
            binding.tvTitle.text = item.title
            binding.rbMovie.rating = item.rating ?: 0f
            setFavoriteImage(item.isBookmarked)
            binding.ivPoster.setOnClickListener {
                onItemClick(item.id)
            }
            binding.btnFavorite.setOnClickListener { onClickItem(item, position) }
        }

        private fun setFavoriteImage(status: Boolean) {
            if (status) {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_unfilled)
            }
        }
    }

    companion object {
        const val RADIUS_CORNER = 25F
    }
}