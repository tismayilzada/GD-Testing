package com.griddynamics.gd_testing.presentation.commits

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.griddynamics.gd_testing.R
import com.griddynamics.gd_testing.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommitsFragment : Fragment(R.layout.fragment_commits) {

    private val viewModel: CommitsViewModel by viewModels()

    private val commitsAdapter: CommitsAdapter by lazy { CommitsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val progressBar = view.findViewById<ProgressBar>(R.id.loading)
        val error = view.findViewById<AppCompatTextView>(R.id.errorTxt)
        view.findViewById<RecyclerView>(R.id.commitsRV).adapter = commitsAdapter



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state().collect {

                    progressBar.isVisible = it is Resource.Loading
                    error.isVisible = it !is Resource.Success

                    when (it) {
                        is Resource.Success -> it.data?.let { data -> commitsAdapter.setList(data) }
                        is Resource.Error -> error.text = it.message
                        else -> {}
                    }
                }
            }
        }
    }

}