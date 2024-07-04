package com.example.githubrepository.domain

import com.example.githubrepository.model.Repo
import com.example.githubrepository.utils.UseCase
import javax.inject.Inject

class ListUserRepositories @Inject constructor(
    private val githubRepository: IGithubRepository
): UseCase<ListUserRepositories.Params, List<Repo>>() {

    override suspend fun execute(parameters: Params): List<Repo> {
        return githubRepository.getUserRepos(parameters.name)
    }

    data class Params(val name: String)
}