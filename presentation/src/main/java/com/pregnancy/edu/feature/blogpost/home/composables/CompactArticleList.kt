package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun CompactArticleList(
    modifier: Modifier = Modifier,
    blogPosts: List<BlogPost> = emptyList(),
    appState: PregnancyAppState
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        blogPosts.forEach { blogPost ->
            CompactArticleCard(
                blogPost = blogPost,
                onBlogPostClick = {
                    appState.navigate("${Destination.Blogs.route}/${it}")
                }
            )
        }
    }
}