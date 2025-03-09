package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.R
import com.pregnancy.edu.feature.blogpost.home.BlogPostFactory

@Preview
@Composable
fun LargeArticleCardPreview() {
    LargeArticleCard(
        blogPost = BlogPostFactory.createBlogPosts().first().copy(nameTags = listOf("Pregnancy", "Nutrition")),
        onBlogPostClick = { }
    )
}

@Composable
fun LargeArticleCard(
    modifier: Modifier = Modifier,
    blogPost: BlogPost,
    onBlogPostClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onBlogPostClick(blogPost.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(blogPost.featuredImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = blogPost.heading,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            error = painterResource(id = R.drawable.img_placeholder)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = blogPost.heading ?: "",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Added tags row here
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            blogPost.nameTags.take(3).forEach { tag ->
                TagChip(text = tag)
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = blogPost.likeQuantity.toString(),
                color = Color.Gray,
                fontSize = 14.sp
            )

            Text(
                text = "•",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Comment",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = blogPost.commentQuantity.toString(),
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "•",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Text(
                text = blogPost.user?.fullName ?: "",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}