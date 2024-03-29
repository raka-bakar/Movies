package com.raka.movies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.databinding.ItemMovieBinding

class HomeMovieAdapter(
    data: List<MovieItemCompact>,
    private val onFavoriteClick: (MovieItemCompact) -> Unit,
    private val onItemClick:(Int) -> Unit
) : RecyclerView.Adapter<HomeMovieAdapter.HomeMovieHolder>() {

    private var listData: MutableList<MovieItemCompact> = data as MutableList<MovieItemCompact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieHolder {
        val v = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeMovieHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: HomeMovieHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    private fun onClickItem(item: MovieItemCompact, position: Int) {
        onFavoriteClick(item)
        notifyItemChanged(position)
    }

    inner class HomeMovieHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: MovieItemCompact,
            position: Int
        ) {
            binding.ivPoster.load(item.posterUrl) {
                transformations(RoundedCornersTransformation(CORNER_RADIUS))
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
        const val CORNER_RADIUS = 25F
    }
}