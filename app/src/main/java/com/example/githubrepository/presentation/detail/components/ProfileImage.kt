package com.example.githubrepository.presentation.detail.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    url: String?
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .allowHardware(false)
            .build(),
        modifier = modifier.clip(CircleShape),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}