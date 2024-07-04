package com.example.githubrepository.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = githubLandingRoute,
        modifier = Modifier
            .fillMaxSize()
    ) {
        githubLandingScreen(
            navigateToGithubRepoDetail = { navController.navigateToGithubRepoDetail(it) }
        )

        githubRepoDetailScreen(
            navigateToGithubWebView = { navController.navigateToGithubWebView(it) }
        )

        githubWebViewScreen()
    }
}