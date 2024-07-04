package com.example.githubrepository.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.githubrepository.presentation.webview.GithubWebViewScreen
import com.google.accompanist.navigation.animation.composable
import java.net.URLEncoder

const val githubWebViewRoute = "github_web_view_route"
const val githubWebViewUrlArg = "github_web_view_url"

fun NavController.navigateToGithubWebView(url: String) {
    this.navigate("$githubWebViewRoute/${URLEncoder.encode(url, "UTF-8")}")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.githubWebViewScreen() {
    composable(
        route = "$githubWebViewRoute/{$githubWebViewUrlArg}",
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
            navArgument(githubWebViewUrlArg) {
                type = NavType.StringType
            }
        )
    ) {
        GithubWebViewScreen()
    }
}