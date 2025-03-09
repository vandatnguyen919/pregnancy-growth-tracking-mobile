package com.pregnancy.edu.feature.blogpost.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.feature.blogpost.detail.composables.BlogPostDetailContent
import com.pregnancy.edu.feature.blogpost.detail.event.BlogPostDetailEvent
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun BlogPostDetailScreen(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState,
    blogPostId: Long,
    blogPostDetailViewModel: BlogPostDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        blogPostDetailViewModel.onTriggerEvent(BlogPostDetailEvent.LoadBlogPost(blogPostId))
    }

    val uiState by blogPostDetailViewModel.uiState.collectAsStateWithLifecycle()

    uiState.blogPost?.let {
        BlogPostDetailContent(
            modifier = modifier,
            blogPost = it
        )
    }
}