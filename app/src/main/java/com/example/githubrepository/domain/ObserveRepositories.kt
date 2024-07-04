package com.example.githubrepository.domain

import androidx.paging.PagingData
import com.example.githubrepository.model.Repo
import com.example.githubrepository.utils.FlowUseCase
import com.example.githubrepository.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveRepositories @Inject constructor(
    private val githubRepository: IGithubRepository
): FlowUseCase<ObserveRepositories.Params, PagingData<Repo>>() {

    override fun execute(parameters: Params): Flow<Result<PagingData<Repo>>> {
        return githubRepository.reposPagingFlow(parameters.query)
            .mapNotNull { Result.Success(it) }
    }

    data class Params(val query: String)
}