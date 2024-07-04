package com.example.githubrepository.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class PagingRemoteKeyDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRemoteKey(remoteKey: PagingRemoteKeyEntity)

    @Query("SELECT * FROM paging_remote_key WHERE api = :apiName")
    abstract suspend fun getPagingDetailsForApi(apiName: String): PagingRemoteKeyEntity?
}