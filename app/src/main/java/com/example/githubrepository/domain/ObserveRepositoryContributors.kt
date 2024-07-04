package com.example.githubrepository.domain

import com.example.githubrepository.model.Contributor
import com.example.githubrepository.utils.FlowUseCase
import com.example.githubrepository.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveRepositoryContributors @Inject constructor(
    private val githubRepository: IGithubRepository
): FlowUseCase<ObserveRepositoryContributors.Params, List<Contributor>>() {

    override fun execute(parameters: Params): Flow<Result<List<Contributor>>> {
        return githubRepository.getRepositoryContributorsFlow(repoUuid = parameters.repoUuid)
            .mapNotNull { Result.Success(it) }
    }

    data class Params(val repoUuid: String)
}