package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun BlogPostContent(
    pagingItems: LazyPagingItems<BlogPost>,
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Featured article
        if (pagingItems.itemCount > 0) {
            item {
                Text(
                    text = stringResource(id = R.string.cd_latest_blogs),
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                pagingItems[0]?.let { featuredPost ->
                    LargeArticleCard(
                        blogPost = featuredPost,
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                }
            }
        }

        // Medium articles in 2x2 grid
        if (pagingItems.itemCount >= 4) {
            item {
                Text(
                    text = stringResource(id = R.string.cd_just_for_you),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    pagingItems[1]?.let { firstMediumPost ->
                        MediumArticleCard(
                            blogPost = firstMediumPost,
                            modifier = Modifier.weight(1f),
                            onBlogPostClick = {
                                appState.navigate("${Destination.Blogs.route}/${it}")
                            }
                        )
                    }
                    pagingItems[2]?.let { secondMediumPost ->
                        MediumArticleCard(
                            blogPost = secondMediumPost,
                            modifier = Modifier.weight(1f),
                            onBlogPostClick = {
                                appState.navigate("${Destination.Blogs.route}/${it}")
                            }
                        )
                    }
                }
            }
        }

        if (pagingItems.itemCount >= 4) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    pagingItems[3]?.let { thirdMediumPost ->
                        MediumArticleCard(
                            blogPost = thirdMediumPost,
                            modifier = Modifier.weight(1f),
                            onBlogPostClick = {
                                appState.navigate("${Destination.Blogs.route}/${it}")
                            }
                        )
                    }
                    pagingItems[4]?.let { fourthMediumPost ->
                        MediumArticleCard(
                            blogPost = fourthMediumPost,
                            modifier = Modifier.weight(1f),
                            onBlogPostClick = {
                                appState.navigate("${Destination.Blogs.route}/${it}")
                            }
                        )
                    }
                }
            }
        }

        // Remaining blog posts as compact cards
        if (pagingItems.itemCount > 4) {
            item {
                Text(
                    text = stringResource(id = R.string.cd_other_blog_posts),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(
                count = pagingItems.itemCount - 5,
                key = pagingItems.itemKey { it.id }
            ) { index ->
                pagingItems[index + 5]?.let { blogPost ->
                    CompactArticleCard(
                        blogPost = blogPost,
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}