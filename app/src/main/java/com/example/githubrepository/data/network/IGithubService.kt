package com.example.githubrepository.data.network

import com.example.githubrepository.data.network.model.GithubRepo
import com.example.githubrepository.data.network.model.ListGithubReposResponse
import com.example.githubrepository.data.network.model.RepoContributor
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IGithubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    suspend fun listRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): ListGithubReposResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{repoName}/contributors")
    suspend fun listContributors(
        @Path("repoName", encoded = true) repoName: String
    ): List<RepoContributor>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}/repos")
    suspend fun getUserRepos(
        @Path("user", encoded = true) user: String
    ): List<GithubRepo>
}