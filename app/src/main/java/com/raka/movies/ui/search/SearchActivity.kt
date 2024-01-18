package com.raka.movies.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.databinding.ActivitySearchBinding
import com.raka.movies.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.movieList.data.collect {
                when (it) {
                    is CallResult.Success -> {
                        it.data?.let { data -> setupFavouriteRecyclerView(data) }
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

        binding.ivBack.setOnClickListener { finish() }
        setupSearchView()
    }

    /**
     *  setup the config of searchview
     */
    private fun setupSearchView() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.setFilter(newText ?: "")
                return false
            }
        })
    }

    /**
     *  Setup Favourite Movie list recyclerview
     *  @param list of the MovieItemCompact
     */
    private fun setupFavouriteRecyclerView(list: List<MovieItemCompact>) {
        searchAdapter = SearchAdapter(list, viewModel::onBookmarkClicked) {
            val intent = Intent(this@SearchActivity, DetailActivity::class.java)
            intent.putExtra("movieId", it)
            startActivity(intent)
        }
        binding.rvAllMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.movieList.refresh()
    }
}