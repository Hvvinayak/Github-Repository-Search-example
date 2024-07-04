package com.example.githubrepository.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubrepository.R
import com.example.githubrepository.presentation.detail.components.ProfileImage
import com.example.githubrepository.presentation.detail.components.UserReposBottomSheet

@Composable
fun GithubRepoDetailScreen(
    viewModel: GithubRepoDetailViewModel = hiltViewModel(),
    openProjectLink: (String) -> Unit
) {
    //repo details
    val repo = viewModel.repoFlow.collectAsStateWithLifecycle().value ?: return

    //contributors list
    val contributors = viewModel.contributors.collectAsStateWithLifecycle().value

    //selected user's repos list
    val userRepos = viewModel.userRepos.value
    val isUserReposLoading = viewModel.isUserReposLoading.value
    val showUserReposBottomSheet = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) {
        //name
        item {
            Text(
                text = repo.fullName,
                fontSize = 22.sp,
                style = TextStyle(
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        //description
        item {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.repo_description_title),
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text(
                    text = repo.description ?: "No description given by user",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                )
            }
        }

        //author
        item {
            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.repo_owner_title),
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Italic
                    )
                )

                ProfileImage(
                    modifier = Modifier.size(20.dp),
                    url = repo.owner.avatarUrl
                )

                Text(
                    text = repo.owner.name,
                    fontSize = 16.sp,
                    color = Color.Black,
                )
            }
        }

        //language
        item {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.repo_language_title),
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text(
                    text = repo.language ?: "No language found",
                    fontSize = 16.sp,
                    color = Color.Black,
                )
            }
        }

        //project url
        item {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.repo_project_url_title),
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text(
                    text = repo.projectUrl ?: "No project url found",
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = if(repo.projectUrl != null) {
                        TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontStyle = FontStyle.Italic
                        )
                    } else LocalTextStyle.current,
                    modifier = Modifier
                        .clickable(repo.projectUrl != null) {
                            repo.projectUrl?.let { openProjectLink(it) }
                        }
                )
            }
        }

        //contributors title
        item {
            Text(
                text = stringResource(R.string.repo_contributors_title),
                fontSize = 16.sp,
                color = Color.Black,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        //contributors list
        itemsIndexed(contributors) { index, contributor ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showUserReposBottomSheet.value = true
                        viewModel.getUserRepositories(contributor)
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${index+1}.)",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                ProfileImage(
                    url = contributor.avatarUrl,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = contributor.name,
                    fontSize = 16.sp,
                    color = Color.Black,
                )
            }
        }
    }

    //selected user repos list
    if(showUserReposBottomSheet.value) {
        UserReposBottomSheet(
            repos = userRepos,
            isLoading = isUserReposLoading,
            onDismissRequest = {
                showUserReposBottomSheet.value = false
                viewModel.bottomSheetDismissed()
            },
        )
    }
}