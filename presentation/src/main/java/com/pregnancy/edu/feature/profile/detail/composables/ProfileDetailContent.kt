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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

// Define pink palette colors
private val LightestPink = Color(0xFFFFF0F3)
private val LightPink = Color(0xFFFFCCD5)
private val MediumLightPink = Color(0xFFFFB3C1)
private val MediumPink = Color(0xFFFF8FA3)
private val MediumDarkPink = Color(0xFFFF758F)
private val DarkPink = Color(0xFFFF4D6D)
private val DarkerPink = Color(0xFFC9184A)
private val DarkestPink = Color(0xFFA4133C)
private val DeepPink = Color(0xFF800F2F)
private val BlackPink = Color(0xFF590D22)

@Composable
fun HomeProfileContent(
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightestPink)
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar and Name
        Box(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp)
                .size(100.dp)
                .background(MediumLightPink, CircleShape)
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

            if (user.verified) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Verified",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                        .background(Color.White, CircleShape)
                        .padding(2.dp),
                    tint = DarkPink
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        user.fullName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = BlackPink
            )
        }

        Text(
            text = "@${user.username}",
            style = MaterialTheme.typography.bodyLarge,
            color = DarkPink
        )

        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val statusColor = if (user.enabled) Color(0xFF27AE60) else Color(0xFFEB5757)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (user.enabled) "Active" else "Inactive",
                style = MaterialTheme.typography.bodySmall,
                color = statusColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (user.verified) {
                Text(
                    text = "• Verified",
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkPink
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section container style
        val sectionStyle = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))

        // Personal Information Section
        Surface(
            modifier = sectionStyle,
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkerPink,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Personal details with icons
                ProfileInfoItem(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = user.email,
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )

                ProfileInfoItem(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = user.phoneNumber ?: "Not provided",
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ProfileInfoItem(
                        icon = Icons.Default.DateRange,
                        label = "Date of Birth",
                        value = user.dateOfBirth?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                            ?: "Not provided",
                        iconTint = DarkPink,
                        labelColor = DeepPink,
                        valueColor = BlackPink
                    )
                }

                ProfileInfoItem(
                    icon = Icons.Default.Person,
                    label = "Gender",
                    value = when (user.gender) {
                        true -> "Male"
                        false -> "Female"
                        else -> "Not specified"
                    },
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )

                ProfileInfoItem(
                    icon = Icons.Default.LocationOn,
                    label = "Nationality",
                    value = user.nationality ?: "Not provided",
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Medical Information Section
        Surface(
            modifier = sectionStyle,
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Medical Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkerPink,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ProfileInfoItem(
                    icon = Icons.Default.Favorite,
                    label = "Blood Type",
                    value = user.bloodType?.displayName ?: "Not provided",
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )

                ProfileInfoItem(
                    icon = Icons.Default.Menu,
                    label = "Symptoms",
                    value = user.symptoms ?: "None",
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Account Information Section
        Surface(
            modifier = sectionStyle,
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Account Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkerPink,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ProfileInfoItem(
                    icon = Icons.Default.AccountCircle,
                    label = "Role",
                    value = user.role,
                    iconTint = DarkPink,
                    labelColor = DeepPink,
                    valueColor = BlackPink
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ProfileInfoItem(
                        icon = Icons.Default.DateRange,
                        label = "Member Since",
                        value = user.createdAt?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                            ?: "Unknown",
                        iconTint = DarkPink,
                        labelColor = DeepPink,
                        valueColor = BlackPink
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}