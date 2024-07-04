package com.example.githubrepository.domain

import androidx.paging.PagingData
import com.example.githubrepository.model.Contributor
import com.example.githubrepository.model.Repo
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    fun reposPagingFlow(query: String): Flow<PagingData<Repo>>

    suspend fun getRepoContributors(repo: Repo)

    suspend fun getUserRepos(name: String): List<Repo>

    fun getRepositoryFlow(uuid: String): Flow<Repo>

    fun getRepositoryContributorsFlow(repoUuid: String): Flow<List<Contributor>>
}