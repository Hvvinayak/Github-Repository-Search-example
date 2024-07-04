package com.example.githubrepository.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RepoEntity::class,
        ContributorEntity::class,
        PagingRemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "github-db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun repoDao(): RepoDao
    abstract fun contributorDao(): ContributorDao
    abstract fun pagingRemoteKeyDao(): PagingRemoteKeyDao
}