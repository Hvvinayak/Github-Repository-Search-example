package com.example.githubrepository.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepository.domain.ListRepositoryContributors
import com.example.githubrepository.domain.ListUserRepositories
import com.example.githubrepository.domain.ObserveRepository
import com.example.githubrepository.domain.ObserveRepositoryContributors
import com.example.githubrepository.model.Contributor
import com.example.githubrepository.model.Repo
import com.example.githubrepository.presentation.navigation.githubRepoUuidArg
import com.example.githubrepository.utils.Result
import com.example.githubrepository.utils.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepoDetailViewModel @Inject constructor(
    private val listUserRepositories: ListUserRepositories,
    listRepositoryContributors: ListRepositoryContributors,
    observeRepository: ObserveRepository,
    observeRepositoryContributors: ObserveRepositoryContributors,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val repoUuid = requireNotNull(savedStateHandle.get<String>(githubRepoUuidArg)) { "repo uuid can't be null" }

    val userRepos = mutableStateOf<List<Repo>>(emptyList())
    val isUserReposLoading = mutableStateOf(false)

    val repoFlow = observeRepository(ObserveRepository.Params(repoUuid))
        .mapNotNull { it.data }
        .onEach {
            listRepositoryContributors(ListRepositoryContributors.Params(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    val contributors = observeRepositoryContributors(ObserveRepositoryContributors.Params(repoUuid))
        .mapNotNull { it.data }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun getUserRepositories(contributor: Contributor) {
        viewModelScope.launch {
            isUserReposLoading.value = true
            when(val result = listUserRepositories(ListUserRepositories.Params(name = contributor.name))) {
                is Result.Success -> {
                    userRepos.value = result.data
                }

                else -> {
                    userRepos.value = emptyList()
                }
            }
            isUserReposLoading.value = false
        }
    }

    fun bottomSheetDismissed() {
        userRepos.value = emptyList()
        isUserReposLoading.value = false
    }
}