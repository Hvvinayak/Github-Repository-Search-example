package com.example.githubrepository.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.githubrepository.data.db.AppDatabase
import com.example.githubrepository.data.db.PagingRemoteKeyEntity
import com.example.githubrepository.data.db.RepoEntity
import com.example.githubrepository.data.network.IGithubService
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val query: String,
    private val appDatabase: AppDatabase,
    private val githubService: IGithubService
): RemoteMediator<Int, RepoEntity>() {

    companion object {
        private const val PAGE_LIMIT = 10
        private const val REMOTE_KEY = "repositories"
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, RepoEntity>): MediatorResult {
        try {
            val remoteKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    appDatabase.withTransaction {
                        appDatabase.pagingRemoteKeyDao().getPagingDetailsForApi(REMOTE_KEY)
                    } ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val pageOffset = (remoteKey?.pageOffset ?: 0).plus(1)

            val response = githubService.listRepos(
                query = query,
                page = pageOffset,
                limit = PAGE_LIMIT
            ).items

            appDatabase.withTransaction {
                appDatabase.pagingRemoteKeyDao()
                    .insertRemoteKey(PagingRemoteKeyEntity(REMOTE_KEY, pageOffset, PAGE_LIMIT))

                if(pageOffset == 1) {
                    appDatabase.repoDao().deleteAll()
                }

                appDatabase.repoDao().insertRepositories(response.map { it.toDbModel() })
            }

            return MediatorResult.Success(endOfPaginationReached = response.isEmpty() || response.size < PAGE_LIMIT)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        }
    }
}