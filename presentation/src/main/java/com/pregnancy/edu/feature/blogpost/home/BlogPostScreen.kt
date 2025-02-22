package com.pregnancy.edu.feature.blogpost.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.feature.blogpost.home.composables.BlogPostContent
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

//@Preview
@Composable
fun BlogPostScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    blogPostViewModel: BlogPostViewModel = hiltViewModel()
) {
    val uiState = blogPostViewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5F5))
    ) {
        BlogPostContent(
            featuredBlogPosts = uiState.value.featuredBlogPosts,
            mediumBlogPosts = uiState.value.mediumBlogPosts,
            compactBlogPosts = uiState.value.compactBlogPosts,
            appState = appState
        )
    }
}