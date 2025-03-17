package com.pregnancy.edu.feature.blogpost.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.R

@Composable
fun CompactArticleCard(
    modifier: Modifier = Modifier,
    blogPost: BlogPost,
    onBlogPostClick: (Long) -> Unit,
    trailingDivider: @Composable () -> Unit = { }
) {
    ConstraintLayout(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onBlogPostClick(blogPost.id) }
            .padding(horizontal = 16.dp)
    ) {
        val (heading, image, divider) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(heading) {
                    start.linkTo(parent.start)
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
            text = blogPost.heading ?: "",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        AsyncImage(
            modifier = Modifier
                .constrainAs(image) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(blogPost.featuredImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = blogPost.pageTitle ?: "",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            error = painterResource(id = R.drawable.img_placeholder)
        )
        Box(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(image.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(1.dp)
                .fillMaxWidth()
        ) {
            trailingDivider()
        }
    }
}