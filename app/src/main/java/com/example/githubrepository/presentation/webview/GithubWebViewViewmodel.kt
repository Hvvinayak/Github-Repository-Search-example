package com.example.githubrepository.presentation.webview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.githubrepository.presentation.navigation.githubWebViewUrlArg
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class GithubWebViewViewmodel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val webViewUrl = requireNotNull(savedStateHandle.get<String>(githubWebViewUrlArg)
        ?.let { URLDecoder.decode(it, "UTF-8") }) { "web view url can't be null" }
}