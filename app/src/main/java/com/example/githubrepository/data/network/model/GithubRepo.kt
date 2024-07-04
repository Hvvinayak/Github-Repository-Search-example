package com.example.githubrepository.data.network.model

import com.example.githubrepository.data.db.OwnerInfo
import com.example.githubrepository.data.db.RepoEntity
import com.example.githubrepository.model.Repo
import com.example.githubrepository.model.RepoOwner
import com.squareup.moshi.Json

data class GithubRepo(
    val id: String,
    @Json(name = "html_url")
    val htmlUrl: String?,
    val owner: Owner,
    @Json(name = "full_name")
    val fullName: String,
    val name: String,
    val description: String?,
    val language: String?,
) {
    fun toDbModel() = RepoEntity(
        uuid = id,
        name = name,
        fullName = fullName,
        description = description,
        language = language,
        projectUrl = htmlUrl,
        owner = OwnerInfo(
            uuid = owner.id,
            login = owner.login,
            avatarUrl = owner.avatarUrl
        )
    )

    fun toDomainModel() = Repo(
        uuid = id,
        name = name,
        fullName = fullName,
        description = description,
        language = language,
        projectUrl = htmlUrl,
        owner = RepoOwner(
            uuid = owner.id,
            name = owner.login,
            avatarUrl = owner.avatarUrl
        )
    )
}

data class Owner(
    val id : String,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)