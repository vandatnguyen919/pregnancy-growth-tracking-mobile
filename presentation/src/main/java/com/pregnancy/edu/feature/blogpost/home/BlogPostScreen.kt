package com.pregnancy.edu.feature.blogpost.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.theme.Pink500
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
    val pagingItems: LazyPagingItems<BlogPost> = blogPostViewModel.pagingDataFlow.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5F5)),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.background(Pink500)
            )
        } else {
            BlogPostContent(
                appState = appState,
                pagingItems = pagingItems
            )
        }
    }
}