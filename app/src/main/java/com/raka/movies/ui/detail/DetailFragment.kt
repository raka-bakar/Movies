package com.raka.movies.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.databinding.FragmentDetailBinding
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.home.HomeMovieAdapter
import com.raka.movies.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding

    private val idMovie: Int
        get() = requireArguments().getInt(ARG_ID_MOVIE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.getMovie(idMovie)

        lifecycleScope.launch {
            viewModel._movie.collect { result ->
                if (result is CallResult.Success) {
                    result.data?.isBookmarked?.let {
                        setBookmarkIcon(it)
                    }
                    setClickListener(result.data)
                    setPosterImage(result.data?.posterUrl)
                    setRating(result.data?.rating ?: 0f)
                    setDate(
                        date = result.data?.releaseDate,
                        runtime = result.data?.runtime
                    )
                    setMovieTitle(result.data?.title, result.data?.releaseDate)
                    setTagsList(result.data?.genres ?: listOf())
                    setOverview(result.data?.overview ?: "")
                    setBudget(result.data?.budget)
                    setRevenue(result.data?.revenue)
                    setLanguage(result.data?.language)
                    setRating(
                        rating = result.data?.rating.toString(),
                        review = result.data?.reviews ?: 0
                    )
                } else if (result is CallResult.Error) {
                    Toast.makeText(
                        this@DetailFragment.context,
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return binding.root
    }

    /**
     *  Set image of movie poster
     *  @param url of String
     */
    private fun setPosterImage(url: String?) {
        binding.ivDetailPoster.load(url) {
            transformations(RoundedCornersTransformation(HomeMovieAdapter.CORNER_RADIUS))
        }
    }

    /**
     *  Set onclick listener to all buttons
     *  @param itemMovie of MovieItemCompact
     */
    private fun setClickListener(itemMovie: MovieItemCompact?) {
        if (itemMovie != null) {
            binding.btnBookmark.setOnClickListener {
                viewModel.onBookmarkClicked(itemMovie)
            }
        }
        binding.btnClose.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    /**
     *  Set bookmark icon based on its status
     *  @param status of bookmark
     */
    private fun setBookmarkIcon(status: Boolean) {
        if (status) {
            binding.btnBookmark.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            binding.btnBookmark.setImageResource(R.drawable.ic_favorite_unfilled)
        }
    }

    /**
     *  Set rating value
     *  @param rating of float
     */
    private fun setRating(rating: Float) {
        binding.rbDetail.rating = rating
    }

    /**
     *  Set date and duration of the movie
     *  @param date of String
     *  @param runtime of Int
     */
    private fun setDate(date: String?, runtime: Int?) {
        val hour = runtime?.div(HOUR)
        val mins = runtime?.rem(HOUR)

        binding.tvDetailDate.text =
            resources.getString(
                R.string.date_movie_detail,
                date?.replace("-", "."), hour, mins
            )
    }

    /**
     *  Set title of the movie and date of the movie
     *  @param title of String
     *  @param date of String
     */
    private fun setMovieTitle(title: String?, date: String?) {
        val year = date?.substring(0, 4)
        val first = "<b> $title </b>"
        val next = "<font color='#808080'> ($year) </font>"
        binding.tvDetailTitle.text = Html.fromHtml(
            first + next,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    /**
     *  Set recycler view of genre
     *  @param list of String
     */
    private fun setTagsList(list: List<String?>) {
        binding.rvTags.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TagsAdapter(list)
            isNestedScrollingEnabled = false
        }
    }

    /**
     *  Set overview of the movie
     *  @param text of String
     */
    private fun setOverview(text: String) {
        binding.tvOverviewValue.text = text
    }

    /**
     *  Set budget of the movie
     *  @param budget of String
     */
    private fun setBudget(budget: Int?) {
        binding.tvBudget.text =
            resources.getString(
                R.string.budget_currency,
                Utils.currencyFormatter(budget.toString())
            )
    }

    /**
     *  Set revenue of the movie
     *  @param revenue of String
     */
    private fun setRevenue(revenue: Int?) {
        binding.tvRevenue.text =
            resources.getString(
                R.string.budget_currency,
                Utils.currencyFormatter(revenue.toString())
            )
    }

    /**
     *  Set language of the movie
     *  @param language of String
     */
    private fun setLanguage(language: String?) {
        binding.tvLanguage.text = Utils.formatLanguage(language ?: "")
    }

    /**
     *  Set language of the movie
     *  @param rating of String
     *  @param review of String
     */
    private fun setRating(rating: String?, review: Int) {
        binding.tvRating.text = resources.getString(R.string.rating_fact, rating, review)
    }

    companion object {
        const val HOUR = 60
        private const val ARG_ID_MOVIE = "argIdMovie"
        fun newInstance(id: Int) = DetailFragment().apply {
            arguments = bundleOf(
                ARG_ID_MOVIE to id
            )
        }
    }
}