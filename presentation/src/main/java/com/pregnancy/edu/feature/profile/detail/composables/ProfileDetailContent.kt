package com.pregnancy.edu.feature.profile.detail.composables


import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.pregnancy.domain.model.authentication.User
import com.pregnancy.edu.R
import java.time.format.DateTimeFormatter

@Composable
fun HomeProfileContent(
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar and Name
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp)
        ) {
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

            if (user.verified == true) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Verified",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.background, CircleShape)
                        .padding(2.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        user.fullName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "@${user.username}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val statusColor = if (user.enabled == true) Color.Green else Color.Red
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (user.enabled == true) "Active" else "Inactive",
                style = MaterialTheme.typography.bodySmall,
                color = statusColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (user.verified == true) {
                Text(
                    text = "• Verified",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Detailed Information Section
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Personal details with icons
        ProfileInfoItem(
            icon = Icons.Default.Email,
            label = "Email",
            value = user.email
        )

        ProfileInfoItem(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = user.phoneNumber ?: "Not provided"
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ProfileInfoItem(
                icon = Icons.Default.DateRange,
                label = "Date of Birth",
                value = user.dateOfBirth?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    ?: "Not provided"
            )
        }

        ProfileInfoItem(
            icon = Icons.Default.Person,
            label = "Gender",
            value = when (user.gender) {
                true -> "Male"
                false -> "Female"
                else -> "Not specified"
            }
        )

        ProfileInfoItem(
            icon = Icons.Default.LocationOn,
            label = "Nationality",
            value = user.nationality ?: "Not provided"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Medical Information Section
        Text(
            text = "Medical Information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp)
        )

        ProfileInfoItem(
            icon = Icons.Default.Favorite,
            label = "Blood Type",
            value = user.bloodType?.displayName ?: "Not provided"
        )

        ProfileInfoItem(
            icon = Icons.Default.Menu,
            label = "Symptoms",
            value = user.symptoms ?: "None"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Account Information Section
        Text(
            text = "Account Information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp)
        )

        ProfileInfoItem(
            icon = Icons.Default.AccountCircle,
            label = "Role",
            value = user.role
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ProfileInfoItem(
                icon = Icons.Default.DateRange,
                label = "Member Since",
                value = user.createdAt?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    ?: "Unknown"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}