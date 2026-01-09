package com.dx.alumnicasestudy.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import coil3.compose.AsyncImage
import com.dx.alumnicasestudy.R
import com.dx.alumnicasestudy.data.domain.models.User

// Profile screen scaffolding (read-only)
// Purpose:
// - Display basic profile fields for an alumni
// - Email visible only (MVP)
// Note: Placeholder only, no editing.

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: ProfileViewModel = hiltViewModel(),
    userId: String?
) {
    val context = LocalContext.current
    var editMode by remember { mutableStateOf(false) }

    val user by vm.user.collectAsState()
    val isCurrentUser by vm.isCurrentUser.collectAsState()

    LaunchedEffect(userId) {
        vm.loadUser(userId)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader(
            isCurrentUser = isCurrentUser,
            onBackClick = { navController.popBackStack() },
            editMode = editMode,
            onEditClick = { editMode = !editMode }
        )
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            if(editMode) {
                Column(
                    Modifier.fillMaxWidth(),
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("email")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("phone")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("LinkedIn, GitHub")
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.ToggleOff, "")
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            } else {
                user?.let { userData ->
                    ProfileContent(user = userData)
                } ?: run {
                    CircularProgressIndicator(modifier = Modifier.padding(48.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    isCurrentUser: Boolean,
    onBackClick: () -> Unit,
    editMode: Boolean,
    onEditClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier.size(24.dp))
            }

            Text(
                 if (isCurrentUser) "My Profile" else "Alumni Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            if(isCurrentUser) {
                IconButton(
                    onClick = onEditClick
                ) {
                    Icon(Icons.Default.Edit, "", modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileContent(user: User) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    //.data() // Use the user's photo URL
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.profile_avatar_placeholder),
                error = painterResource(id = R.drawable.profile_error_placeholder),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Graduated: ${user.graduation_year}", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(Modifier.height(24.dp))
        ProfileInfoRow("Role", user.job_title)
        ProfileInfoRow("Company", user.company)
        ProfileInfoRow("Department", user.department)
        ProfileInfoRow("Email", user.email)
        // Add more fields as needed, e.g., LinkedIn, etc.
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}