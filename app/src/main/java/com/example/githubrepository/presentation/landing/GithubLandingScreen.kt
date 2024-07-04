package com.example.githubrepository.presentation.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubrepository.R
import com.example.githubrepository.model.Repo
import com.example.githubrepository.presentation.landing.components.GithubRepoCard

@Composable
fun GithubLandingScreen(
    viewModel: GithubLandingViewModel = hiltViewModel(),
    showRepoDetails: (String) -> Unit
) {
    //search query
    val searchQuery = viewModel.searchQueryFlow.collectAsStateWithLifecycle().value

    //paging list
    val repos: LazyPagingItems<Repo> = viewModel.reposFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        //screen title
        Text(
            text = stringResource(id = R.string.github_landing_title),
            fontSize = 22.sp,
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp),
            color = Color.Black
        )

        //search field
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                viewModel.onSearchQueryChanged(it)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
                .background(color = Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                if(searchQuery.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.search_hint),
                        fontSize = 16.sp,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                innerTextField()
            }
        )

        //repositories list
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repos.itemCount) { index ->
                val repo = repos[index] ?: return@items

                GithubRepoCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    repo = repo,
                    showRepoDetails = { showRepoDetails(repo.uuid) }
                )
            }

            item {
                //placeholder text if list is empty
                if (repos.loadState.refresh is LoadState.NotLoading && repos.loadState.prepend.endOfPaginationReached &&
                    repos.loadState.append.endOfPaginationReached && repos.itemCount == 0
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = if(searchQuery.isBlank())
                                "Search something to load repositories"
                            else
                                (repos.loadState.append as? LoadState.Error)?.error?.message ?: "No results found",
                            color = Color.Black.copy(0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                //load state when list is getting refreshed
                when (repos.loadState.refresh) {
                    is LoadState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = if(searchQuery.isBlank())
                                    "Search something to load repositories"
                                else
                                    (repos.loadState.append as? LoadState.Error)?.error?.message ?: "No results found",
                                color = Color.Black.copy(0.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is LoadState.Loading -> {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                trackColor = Color.Black.copy(alpha = 0.3f),
                                color = Color.Black,
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    }

                    else -> Unit
                }

                //load state when list is getting appended
                when (repos.loadState.append) {
                    is LoadState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = if (searchQuery.isBlank())
                                    "Search something to see repositories"
                                else
                                    (repos.loadState.append as LoadState.Error).error.message ?: "No results found",
                                color = Color.Black.copy(0.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is LoadState.Loading -> {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                trackColor = Color.Black.copy(alpha = 0.3f),
                                color = Color.Black,
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    }

                    else -> Unit
                }
            }

            //extra spacing at the bottom
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}