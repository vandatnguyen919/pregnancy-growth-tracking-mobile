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

@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)
            if (currentRowWidth + placeable.width > constraints.maxWidth) {
                rows.add(currentRow)
                currentRow = mutableListOf()
                currentRowWidth = 0
            }
            currentRow.add(placeable)
            currentRowWidth += placeable.width
        }
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
        }

        val height = rows.sumOf { row ->
            row.maxOf { it.height }
        }

        layout(constraints.maxWidth, height) {
            var y = 0
            rows.forEach { row ->
                var x = 0
                row.forEach { placeable ->
                    placeable.place(x, y)
                    x += placeable.width
                }
                y += row.maxOf { it.height }
            }
        }
    }
}