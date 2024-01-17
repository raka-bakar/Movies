package com.raka.movies.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.movies.R
import com.raka.movies.data.CallResult
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.databinding.ActivityHomeBinding
import com.raka.movies.ui.detail.DetailActivity
import com.raka.movies.ui.search.SearchActivity
import com.raka.movies.utils.getEmojiByUnicode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setProfile()
        lifecycleScope.launch {
            viewModel.favouriteMoviesList.data.collect {
                when (it) {
                    is CallResult.Success -> {
                        it.data?.let { data -> setupFavouriteRecyclerView(data) }
                    }

                    is CallResult.Error -> {
                        Timber.e(it.message)
                        setupFavouriteRecyclerView(it.data ?: listOf())
                    }

                    else -> {
                        // CallResult.initial , do nothing
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.staffPickList.data.collect {
                when (it) {
                    is CallResult.Success -> {
                        it.data?.let { data -> setupStaffPickRecyclerView(data) }
                    }

                    is CallResult.Error -> {
                        Timber.e(it.message)
                    }

                    else -> {
                        // CallResult.initial , do nothing
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.favouriteMoviesList.refresh()
        viewModel.staffPickList.refresh()
    }

    /**
     *  Setup Favourite Movie list recyclerview
     *  @param list of the MovieItemCompact
     */
    private fun setupFavouriteRecyclerView(list: List<MovieItemCompact>) {
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeFavoriteAdapter(list) {
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra("movieId", it.id)
                startActivity(intent)
            }
        }
    }

    /**
     *  Setup Favourite Movie list recyclerview
     *  @param link of the MovieItemCompact
     */
    private fun setupStaffPickRecyclerView(list: List<MovieItemCompact>) {
        binding.rvStaffPicks.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeMovieAdapter(list, viewModel::addBookmark) {
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra("movieId", it)
                startActivity(intent)
            }
        }
    }

    /**
     *  Set user profile data, for now the data is hardcoded
     */
    private fun setProfile() {
        binding.ivProfile.background =
            AppCompatResources.getDrawable(this, R.drawable.avatar_profile)
        val greeting = getString(R.string.greetings) + getEmojiByUnicode(waveEmoji)
        binding.tvGreeting.text = greeting
        binding.tvUsername.text = getString(R.string.default_name)
        binding.tvFavorites.text = HtmlCompat.fromHtml(
            getString(R.string.your_favourite),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        binding.tvTitleStaff.text = HtmlCompat.fromHtml(
            getString(R.string.our_staff_pick),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val waveEmoji: Int = 0x1F44B
    }
}