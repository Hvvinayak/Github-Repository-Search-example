package com.example.githubrepository.model

data class Repo(
    val uuid: String,
    val name: String,
    val fullName: String,
    val description: String?,
    val language: String?,
    val projectUrl: String?,
    val owner: RepoOwner
)