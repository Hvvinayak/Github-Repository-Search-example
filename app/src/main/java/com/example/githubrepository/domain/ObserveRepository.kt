package com.example.githubrepository.domain

import com.example.githubrepository.model.Repo
import com.example.githubrepository.utils.FlowUseCase
import com.example.githubrepository.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveRepository @Inject constructor(
    private val githubRepository: IGithubRepository
): FlowUseCase<ObserveRepository.Params, Repo>() {

    override fun execute(parameters: Params): Flow<Result<Repo>> {
        return githubRepository.getRepositoryFlow(parameters.uuid)
            .mapNotNull { Result.Success(it) }
    }

    data class Params(val uuid: String)
}