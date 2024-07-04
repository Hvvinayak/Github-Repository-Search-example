package com.example.githubrepository.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubrepository.model.Repo
import com.example.githubrepository.model.RepoOwner

@Entity(tableName = "repositories")
data class RepoEntity(
    @PrimaryKey
    val uuid: String,
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val description: String?,
    val language: String?,
    @ColumnInfo(name = "project_url")
    val projectUrl: String?,
    @Embedded(prefix = "owner_")
    val owner: OwnerInfo
) {
    fun toDomainModel() = Repo(
        uuid = uuid,
        name = name,
        fullName = fullName,
        description = description,
        language = language,
        projectUrl = projectUrl,
        owner = RepoOwner(
            uuid = owner.uuid,
            name = owner.login,
            avatarUrl = owner.avatarUrl
        )
    )
}

data class OwnerInfo(
    val uuid: String,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?
)