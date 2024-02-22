package com.raka.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.databinding.FragmentHomeBinding
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.detail.DetailFragment
import com.raka.movies.ui.search.SearchFragment
import com.raka.movies.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        return binding.root
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
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frameContainerView, DetailFragment.newInstance(it.id))
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    /**
     *  Setup Favourite Movie list recyclerview
     *  @param list of the MovieItemCompact
     */
    private fun setupStaffPickRecyclerView(list: List<MovieItemCompact>) {
        binding.rvStaffPicks.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeMovieAdapter(list, viewModel::onBookmarkClicked) {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frameContainerView, DetailFragment.newInstance(it))
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    /**
     *  Set user profile data, for now the data is hardcoded
     */
    private fun setProfile() {
        binding.ivProfile.background =
            context?.let { AppCompatResources.getDrawable(it, R.drawable.avatar_boy_male) }
        val greeting = getString(R.string.greetings) + Utils.getEmojiByUnicode(waveEmoji)
        binding.tvGreeting.text = greeting
        binding.tvUsername.text = getString(R.string.default_name)
        binding.tvFavorites.text = HtmlCompat.fromHtml(
            getString(R.string.your),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        binding.tvTitleStaff.text = HtmlCompat.fromHtml(
            getString(R.string.our_staff_pick),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        binding.btnSearch.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameContainerView, SearchFragment.newInstance())
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {
        const val waveEmoji: Int = 0x1F44B
    }
}