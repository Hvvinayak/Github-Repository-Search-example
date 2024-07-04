package com.example.githubrepository.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubrepository.R
import com.example.githubrepository.model.Repo
import com.example.githubrepository.presentation.landing.components.GithubRepoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReposBottomSheet(
    onDismissRequest: () -> Unit,
    repos: List<Repo>,
    isLoading: Boolean
) {
    val modalBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = modalBottomSheet,
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 48.dp)
    ) {
        //title
        Text(
            text = stringResource(R.string.user_repositories_title),
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        //repositories list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(repos) {
                GithubRepoCard(
                    modifier = Modifier.fillMaxWidth(),
                    repo = it,
                    showRepoDetails = {  }
                )
            }

            //load state
            if(repos.isEmpty()) {
                item {
                    if(isLoading) {
                        CircularProgressIndicator(
                            trackColor = Color.Black.copy(alpha = 0.3f),
                            color = Color.Black,
                            strokeWidth = 3.dp,
                            strokeCap = StrokeCap.Round
                        )
                    } else {
                        Text(
                            text = "No User Repository found",
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}