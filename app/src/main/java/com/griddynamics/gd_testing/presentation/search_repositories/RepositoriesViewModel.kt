package com.griddynamics.gd_testing.presentation.search_repositories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griddynamics.gd_testing.common.Constants
import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.domain.model.RepositoryModel
import com.griddynamics.gd_testing.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
) : ViewModel() {

    suspend fun state(): StateFlow<Resource<List<RepositoryModel>>> =
        savedStateHandle.getStateFlow<String?>(Constants.SEARCH_QUERY, null)
            .flatMapLatest { query ->
                query?.let {
                    repository.getUserRepositories(it)
                } ?: flow { emit(Resource.Initial()) }
            }.stateIn(viewModelScope)

    fun setQuery(query: String) {
        savedStateHandle[Constants.SEARCH_QUERY] = query
    }

}