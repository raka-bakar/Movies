package com.raka.movies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.databinding.FragmentSearchBinding
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

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

        binding.ivBack.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        setupSearchView()
        return binding.root
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
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameContainerView, DetailFragment.newInstance(it))
                addToBackStack(null)
                commit()
            }
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

    companion object {
        fun newInstance() = SearchFragment()
    }
}