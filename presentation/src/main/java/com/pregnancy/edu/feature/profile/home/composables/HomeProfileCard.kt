package com.pregnancy.edu.feature.profile.home.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.pregnancy.domain.model.BloodType
import com.pregnancy.domain.model.authentication.User
import com.pregnancy.edu.R
import java.time.LocalDateTime

@Composable
fun HomeProfileCard(
    user: User,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Profile Card
    Card(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable(onClick = onCardClick)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim, CircleShape)
            ) {
                // Avatar
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = user.avatarUrl ?: "")
                            .apply(block = { placeholder(R.drawable.img_placeholder) })
                            .build()
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                if (user.verified) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Verified",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.background, CircleShape)
                            .padding(2.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Full Name
            user.fullName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Username
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeProfileCardPreview() {
    // Sample user data
    val sampleUser = User(
        id = 1,
        email = "john.doe@example.com",
        username = "johndoe",
        phoneNumber = "+1234567890",
        fullName = "John Doe",
        dateOfBirth = LocalDateTime.of(1990, 5, 15, 0, 0),
        avatarUrl = "https://randomuser.me/api/portraits/men/1.jpg",
        gender = true, // true for male
        bloodType = BloodType.O_POSITIVE,
        symptoms = "None",
        nationality = "American",
        createdAt = LocalDateTime.now().minusMonths(6),
        verified = true,
        enabled = true,
        role = "USER"
    )
    HomeProfileCard(
        user = sampleUser,
        onCardClick = {}
    )
}