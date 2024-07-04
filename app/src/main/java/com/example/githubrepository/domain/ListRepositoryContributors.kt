package com.example.githubrepository.domain

import com.example.githubrepository.model.Repo
import com.example.githubrepository.utils.UseCase
import javax.inject.Inject

class ListRepositoryContributors @Inject constructor(
    private val githubRepository: IGithubRepository
): UseCase<ListRepositoryContributors.Params, Unit>() {

    override suspend fun execute(parameters: Params) {
        githubRepository.getRepoContributors(parameters.repo)
    }

    data class Params(val repo: Repo)
}