package com.example.githubrepository.presentation.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githubrepository.domain.ObserveRepositories
import com.example.githubrepository.utils.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@HiltViewModel
class GithubLandingViewModel @Inject constructor(
    observeRepositories: ObserveRepositories
): ViewModel() {

    val searchQueryFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val reposFlow = searchQueryFlow.debounce(800L).flatMapLatest {
        observeRepositories(ObserveRepositories.Params(it))
            .mapNotNull { it.data }
    }
    .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.value = query
    }
}