package com.example.githubrepository.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.githubrepository.presentation.detail.GithubRepoDetailScreen
import com.google.accompanist.navigation.animation.composable

const val githubRepoDetailRoute = "github_repo_detail_route"
const val githubRepoUuidArg = "github_repo_uuid"

fun NavController.navigateToGithubRepoDetail(uuid: String) {
    this.navigate("$githubRepoDetailRoute/$uuid")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.githubRepoDetailScreen(
    navigateToGithubWebView: (String) -> Unit,
) {
    composable(
        route = "$githubRepoDetailRoute/{$githubRepoUuidArg}",
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 2000 })
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -2000 })
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -2000 })
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 2000 })
        },
        arguments = listOf(
            navArgument(githubRepoUuidArg) {
                type = NavType.StringType
            }
        )
    ) {
        GithubRepoDetailScreen(openProjectLink = navigateToGithubWebView)
    }
}