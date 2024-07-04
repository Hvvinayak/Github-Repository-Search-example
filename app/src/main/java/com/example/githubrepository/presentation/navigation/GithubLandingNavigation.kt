package com.example.githubrepository.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import com.example.githubrepository.presentation.landing.GithubLandingScreen
import com.google.accompanist.navigation.animation.composable

const val githubLandingRoute = "github_landing_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.githubLandingScreen(
    navigateToGithubRepoDetail: (String) -> Unit
) {
    composable(
        route = githubLandingRoute,
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
        }
    ) {
        GithubLandingScreen(
            showRepoDetails = navigateToGithubRepoDetail
        )
    }
}