package com.example.githubrepository.data.network.model

import com.example.githubrepository.data.db.ContributorEntity
import com.squareup.moshi.Json

data class RepoContributor(
    val id: String,
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String?
) {
    fun toDbModel(repoUuid: String) = ContributorEntity(
        uuid = id,
        repoUuid = repoUuid,
        name = login,
        avatarUrl = avatarUrl
    )
}