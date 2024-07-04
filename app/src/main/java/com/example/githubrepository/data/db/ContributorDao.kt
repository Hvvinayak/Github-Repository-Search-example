package com.example.githubrepository.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContributorDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertContributors(contributorEntity: List<ContributorEntity>)

    @Query("DELETE FROM contributors WHERE repo_uuid = :repoUuid")
    abstract suspend fun deleteAll(repoUuid: String)

    @Query("SELECT * FROM contributors WHERE repo_uuid = :repoUuid")
    abstract fun getContributorsFlow(repoUuid: String): Flow<List<ContributorEntity>>
}