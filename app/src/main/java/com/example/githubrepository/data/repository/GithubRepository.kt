package com.example.githubrepository.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubrepository.data.db.AppDatabase
import com.example.githubrepository.data.network.IGithubService
import com.example.githubrepository.data.paging.RepositoriesRemoteMediator
import com.example.githubrepository.domain.IGithubRepository
import com.example.githubrepository.model.Contributor
import com.example.githubrepository.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val githubService: IGithubService
): IGithubRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun reposPagingFlow(query: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSize = 10
            ),
            remoteMediator = RepositoriesRemoteMediator(
                query = query,
                appDatabase = appDatabase,
                githubService = githubService
            )
        ) {
            appDatabase.repoDao().getRepositoriesPagingFlow()
        }.flow.map { it.map { it.toDomainModel() } }
    }

    override suspend fun getRepoContributors(repo: Repo) {
        githubService.listContributors(repo.fullName).apply {
            appDatabase.contributorDao().deleteAll(repo.uuid)
            appDatabase.contributorDao().insertContributors(this.map { it.toDbModel(repo.uuid) })
        }
    }

    override suspend fun getUserRepos(name: String): List<Repo> {
        return githubService.getUserRepos(name).map { it.toDomainModel() }
    }

    override fun getRepositoryFlow(uuid: String): Flow<Repo> {
        return appDatabase.repoDao().getRepositoryFlowByUuid(uuid)
            .mapNotNull { it.toDomainModel() }
    }

    override fun getRepositoryContributorsFlow(repoUuid: String): Flow<List<Contributor>> {
        return appDatabase.contributorDao().getContributorsFlow(repoUuid)
            .mapNotNull { it.map { it.toDomainModel() } }
    }
}