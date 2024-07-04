package com.example.githubrepository.presentation.landing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubrepository.R
import com.example.githubrepository.model.Repo

@Composable
fun GithubRepoCard(
    modifier: Modifier = Modifier,
    repo: Repo,
    showRepoDetails: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .clickable { showRepoDetails() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //name
        Text(
            text = stringResource(R.string.repo_name, repo.fullName),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        //name
        Text(
            text = stringResource(R.string.repo_language, repo.language ?: "Not Specified"),
            fontSize = 14.sp,
            color = Color.Black,
        )

        //owner
        Text(
            text = stringResource(R.string.repo_owner, repo.owner.name),
            fontSize = 14.sp,
            color = Color.Black,
        )
    }
}