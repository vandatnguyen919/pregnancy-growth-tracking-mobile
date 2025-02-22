package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.blogpost.home.BlogPostFactory
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Preview
@Composable
fun BlogPostContentPreview() {
    val blogPosts = BlogPostFactory.createBlogPosts()
    val featuredBlogPosts = blogPosts.take(1)
    val mediumBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) }.take(4)
    val compactBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) && !mediumBlogPosts.contains(it) }

    BlogPostContent(
        featuredBlogPosts = featuredBlogPosts,
        mediumBlogPosts = mediumBlogPosts,
        compactBlogPosts = compactBlogPosts,
        appState = rememberAppState()
    )
}


@Composable
fun BlogPostContent(
    modifier: Modifier = Modifier,
    featuredBlogPosts: List<BlogPost> = emptyList(),
    mediumBlogPosts: List<BlogPost> = emptyList(),
    compactBlogPosts: List<BlogPost> = emptyList(),
    appState: PregnancyAppState
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Featured article
        if (featuredBlogPosts.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.cd_latest_blogs),
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LargeArticleCard(
                    blogPost = featuredBlogPosts.first(),
                    onBlogPostClick = {
                        appState.navigate("${Destination.Blogs.route}/${it}")
                    }
                )
            }
        }

        // Medium articles in 2x2 grid
        if (mediumBlogPosts.size >= 2) {
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
                    MediumArticleCard(
                        blogPost = mediumBlogPosts[0],
                        modifier = Modifier.weight(1f),
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                    MediumArticleCard(
                        blogPost = mediumBlogPosts[1],
                        modifier = Modifier.weight(1f),
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                }
            }
        }

        if (mediumBlogPosts.size >= 4) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MediumArticleCard(
                        blogPost = mediumBlogPosts[2],
                        modifier = Modifier.weight(1f),
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                    MediumArticleCard(
                        blogPost = mediumBlogPosts[3],
                        modifier = Modifier.weight(1f),
                        onBlogPostClick = {
                            appState.navigate("${Destination.Blogs.route}/${it}")
                        }
                    )
                }
            }
        }

        // Compact article list
        if (compactBlogPosts.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.cd_other_blog_posts),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                CompactArticleList(
                    blogPosts = compactBlogPosts,
                    appState = appState
                )
            }
        }
    }
}