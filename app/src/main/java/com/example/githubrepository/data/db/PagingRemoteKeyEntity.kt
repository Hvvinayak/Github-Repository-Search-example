package com.example.githubrepository.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paging_remote_key")
data class PagingRemoteKeyEntity(
    @PrimaryKey
    val api: String,
    @ColumnInfo(name = "page_offset")
    val pageOffset: Int,
    @ColumnInfo(name = "page_limit")
    val pageLimit: Int
)