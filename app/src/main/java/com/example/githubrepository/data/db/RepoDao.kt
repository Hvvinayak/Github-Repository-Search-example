package com.example.githubrepository.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RepoDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRepositories(repoEntity: List<RepoEntity>)

    @Query("DELETE FROM repositories")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM repositories")
    abstract fun getRepositoriesPagingFlow(): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repositories WHERE uuid = :uuid")
    abstract fun getRepositoryFlowByUuid(uuid: String): Flow<RepoEntity>
}