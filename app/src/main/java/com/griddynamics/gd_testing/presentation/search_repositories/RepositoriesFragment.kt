package com.griddynamics.gd_testing.presentation.search_repositories

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.griddynamics.gd_testing.R
import com.griddynamics.gd_testing.common.Constants
import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.data.remote.GithubAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {

    private val viewModel: RepositoriesViewModel by viewModels()

    private val mAdapter: RepositoriesAdapter by lazy { RepositoriesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBar = view.findViewById<SearchView>(R.id.searchView)
        val progressBar = view.findViewById<ProgressBar>(R.id.loading)
        val error = view.findViewById<AppCompatTextView>(R.id.errorTxt)
        val rv = view.findViewById<RecyclerView>(R.id.repositoriesRV)

        mAdapter.onItemClicked {
            findNavController().navigate(
                R.id.action_repositoriesFragment_to_commitsFragment,
                bundleOf(Constants.REPOSITORY to it)
            )
        }

        rv.adapter = mAdapter


        view.findViewById<AppCompatImageButton>(R.id.searchBtn).setOnClickListener {
            searchBar.query.toString().let {
                if (it.isNotBlank()) viewModel.setQuery(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state().collect {

                    progressBar.isVisible = it is Resource.Loading
                    error.isVisible = it !is Resource.Success

                    when (it) {
                        is Resource.Success -> it.data?.let { data -> mAdapter.setList(data) }
                        is Resource.Error -> {
                            mAdapter.clearList()
                            error.text = it.message
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}